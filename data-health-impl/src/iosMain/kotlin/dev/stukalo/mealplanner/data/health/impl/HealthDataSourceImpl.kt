package dev.stukalo.mealplanner.data.health.impl

import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.exception.HealthDataException
import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.data.health.model.HealthPermissionStatusDataModel
import dev.stukalo.mealplanner.data.health.model.HealthServiceStatusDataModel
import dev.stukalo.mealplanner.data.health.model.NutritionHealthModel
import dev.stukalo.mealplanner.data.health.model.WeightHealthModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toNSDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.HealthKit.HKAuthorizationStatusSharingAuthorized
import platform.HealthKit.HKHealthStore
import platform.HealthKit.HKObjectType
import platform.HealthKit.HKQuantity
import platform.HealthKit.HKQuantitySample
import platform.HealthKit.HKQuantityType
import platform.HealthKit.HKQuantityTypeIdentifierBodyMass
import platform.HealthKit.HKQuantityTypeIdentifierDietaryCarbohydrates
import platform.HealthKit.HKQuantityTypeIdentifierDietaryEnergyConsumed
import platform.HealthKit.HKQuantityTypeIdentifierDietaryFatTotal
import platform.HealthKit.HKQuantityTypeIdentifierDietaryProtein
import platform.HealthKit.HKQuantityTypeIdentifierStepCount
import platform.HealthKit.HKQuery
import platform.HealthKit.HKQueryOptionNone
import platform.HealthKit.HKSampleQuery
import platform.HealthKit.HKStatisticsOptionCumulativeSum
import platform.HealthKit.HKStatisticsQuery
import platform.HealthKit.HKUnit
import platform.HealthKit.predicateForSamplesWithStartDate
import kotlin.time.Instant

/**
 * iOS implementation of [HealthDataSource] using HealthKit API.
 */
internal class HealthDataSourceImpl : HealthDataSource {

    private val healthStore = if (HKHealthStore.isHealthDataAvailable()) HKHealthStore() else null

    private val stepType = HKQuantityType.quantityTypeForIdentifier(HKQuantityTypeIdentifierStepCount)!!
    private val weightType = HKQuantityType.quantityTypeForIdentifier(HKQuantityTypeIdentifierBodyMass)!!
    private val energyType = HKQuantityType.quantityTypeForIdentifier(HKQuantityTypeIdentifierDietaryEnergyConsumed)!!
    private val proteinType = HKQuantityType.quantityTypeForIdentifier(HKQuantityTypeIdentifierDietaryProtein)!!
    private val fatType = HKQuantityType.quantityTypeForIdentifier(HKQuantityTypeIdentifierDietaryFatTotal)!!
    private val carbType = HKQuantityType.quantityTypeForIdentifier(HKQuantityTypeIdentifierDietaryCarbohydrates)!!

    private val readPermissions = setOf(stepType, weightType, energyType, proteinType, fatType, carbType)
    private val writePermissions = setOf(weightType, energyType, proteinType, fatType, carbType)

    override suspend fun isAvailable(): Boolean = HKHealthStore.isHealthDataAvailable()

    override suspend fun getStatus(): HealthServiceStatusDataModel = if (isAvailable()) {
        HealthServiceStatusDataModel.AVAILABLE
    } else {
        HealthServiceStatusDataModel.NOT_SUPPORTED
    }

    override suspend fun hasPermissions(): Boolean {
        val store = healthStore ?: return false
        val writeTypes = writePermissions
        return writeTypes.all { type ->
            store.authorizationStatusForType(type) == HKAuthorizationStatusSharingAuthorized
        }
    }

    override suspend fun requestPermissions(permissionId: String?): Result<Set<HealthPermissionDataModel>> {
        val store = healthStore ?: return Result.failure(HealthDataException.ServiceUnavailable())
        val deferred = CompletableDeferred<Result<Set<HealthPermissionDataModel>>>()

        store.requestAuthorizationToShareTypes(
            typesToShare = writePermissions.map { it as HKObjectType }.toSet(),
            readTypes = readPermissions.map { it as HKObjectType }.toSet()
        ) { success, error ->
            if (success) {
                deferred.complete(Result.success(HealthPermissionDataModel.entries.toSet()))
            } else {
                deferred.complete(Result.failure(HealthDataException.SyncError(Exception(error?.localizedDescription))))
            }
        }

        return deferred.await()
    }

    override fun getStepsAsFlow(date: LocalDate): Flow<Int> = callbackFlow {
        val store = healthStore
        if (store == null) {
            trySend(0)
            close()
            return@callbackFlow
        }

        val startOfDay = date.atStartOfDayIn(TimeZone.currentSystemDefault()).toNSDate()
        val endOfDay = NSCalendar.currentCalendar.dateByAddingUnit(
            unit = NSCalendarUnitDay,
            value = 1,
            toDate = startOfDay,
            options = 0.toULong()
        )!!

        // Correctly called extension on HKQuery companion
        val predicate = HKQuery.predicateForSamplesWithStartDate(startOfDay, endOfDay, HKQueryOptionNone)

        val query = HKStatisticsQuery(
            quantityType = stepType,
            quantitySamplePredicate = predicate,
            options = HKStatisticsOptionCumulativeSum
        ) { _, statistics, error ->
            if (error != null) {
                trySend(0)
            } else {
                val sum = statistics?.sumQuantity()?.doubleValueForUnit(HKUnit.unitFromString("count")) ?: 0.0
                trySend(sum.toInt())
            }
        }

        store.executeQuery(query)

        // In a real app, you might want to observe changes. For now, we just fetch once.
        // To make it a real flow that updates, we'd need HKObserverQuery.

        awaitClose {
            store.stopQuery(query)
        }
    }

    override suspend fun fetchWeightHistory(startTime: Instant): Result<List<WeightHealthModel>> {
        val store = healthStore ?: return Result.failure(HealthDataException.ServiceUnavailable())
        val deferred = CompletableDeferred<Result<List<WeightHealthModel>>>()

        // Correctly called extension on HKQuery companion
        val predicate = HKQuery.predicateForSamplesWithStartDate(startTime.toNSDate(), null, HKQueryOptionNone)
        val query = HKSampleQuery(
            sampleType = weightType,
            predicate = predicate,
            limit = 0.toULong(),
            sortDescriptors = null
        ) { _, samples, error ->
            if (error != null) {
                deferred.complete(Result.failure(HealthDataException.SyncError(Exception(error.localizedDescription))))
            } else {
                // Fixed unchecked cast warning by using filterIsInstance
                val models = samples?.filterIsInstance<HKQuantitySample>()?.map { sample ->
                    val weightKg = sample.quantity.doubleValueForUnit(HKUnit.unitFromString("kg"))
                    WeightHealthModel(
                        date = sample.startDate.toKotlinInstant().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                        weight = weightKg
                    )
                } ?: emptyList()
                deferred.complete(Result.success(models))
            }
        }

        store.executeQuery(query)
        return deferred.await()
    }

    override suspend fun fetchNutrientChanges(startTime: Instant): Result<Map<LocalDate, NutritionHealthModel>> {
        val store = healthStore ?: return Result.failure(HealthDataException.ServiceUnavailable())

        val types = listOf(energyType, proteinType, fatType, carbType)
        // Correctly called extension on HKQuery companion
        val predicate = HKQuery.predicateForSamplesWithStartDate(startTime.toNSDate(), null, HKQueryOptionNone)

        val allSamples = mutableListOf<HKQuantitySample>()

        try {
            types.forEach { type ->
                val deferred = CompletableDeferred<List<HKQuantitySample>>()
                val query = HKSampleQuery(
                    sampleType = type,
                    predicate = predicate,
                    limit = 0.toULong(),
                    sortDescriptors = null
                ) { _, samples, error ->
                    if (error != null) {
                        deferred.completeExceptionally(Exception(error.localizedDescription))
                    } else {
                        // Fixed unchecked cast warning by using filterIsInstance
                        deferred.complete(samples?.filterIsInstance<HKQuantitySample>() ?: emptyList())
                    }
                }
                store.executeQuery(query)
                allSamples.addAll(deferred.await())
            }

            val nutritionMap = allSamples.groupBy { sample ->
                sample.startDate.toKotlinInstant().toLocalDateTime(TimeZone.currentSystemDefault()).date
            }.mapValues { (date, samples) ->
                NutritionHealthModel(
                    date = date,
                    calories = samples.filter {
                        it.sampleType == energyType
                    }.sumOf { it.quantity.doubleValueForUnit(HKUnit.unitFromString("kcal")) },
                    proteins = samples.filter {
                        it.sampleType == proteinType
                    }.sumOf { it.quantity.doubleValueForUnit(HKUnit.unitFromString("g")) },
                    fats = samples.filter {
                        it.sampleType == fatType
                    }.sumOf { it.quantity.doubleValueForUnit(HKUnit.unitFromString("g")) },
                    carbohydrates = samples.filter {
                        it.sampleType == carbType
                    }.sumOf { it.quantity.doubleValueForUnit(HKUnit.unitFromString("g")) }
                )
            }
            return Result.success(nutritionMap)
        } catch (e: Exception) {
            return Result.failure(HealthDataException.SyncError(e))
        }
    }

    override suspend fun writeWeight(weight: WeightHealthModel): Result<Unit> {
        val store = healthStore ?: return Result.failure(HealthDataException.ServiceUnavailable())
        val deferred = CompletableDeferred<Result<Unit>>()

        val quantity = HKQuantity.quantityWithUnit(
            HKUnit.unitFromString("kg"),
            weight.weight
        )
        val sample = HKQuantitySample.quantitySampleWithType(
            quantityType = weightType,
            quantity = quantity,
            startDate = weight.date.atStartOfDayIn(TimeZone.currentSystemDefault()).toNSDate(),
            endDate = weight.date.atStartOfDayIn(TimeZone.currentSystemDefault()).toNSDate()
        )

        store.saveObject(sample) { success, error ->
            if (success) {
                deferred.complete(Result.success(Unit))
            } else {
                deferred.complete(
                    Result.failure(HealthDataException.WriteError(Exception(error?.localizedDescription)))
                )
            }
        }

        return deferred.await()
    }

    override suspend fun writeNutrition(date: LocalDate, progress: NutritionHealthModel): Result<Unit> {
        val store = healthStore ?: return Result.failure(HealthDataException.ServiceUnavailable())
        val deferred = CompletableDeferred<Result<Unit>>()

        val start = date.atStartOfDayIn(TimeZone.currentSystemDefault()).toNSDate()

        val energyQuantity = HKQuantity.quantityWithUnit(HKUnit.unitFromString("kcal"), progress.calories)
        val proteinQuantity = HKQuantity.quantityWithUnit(HKUnit.unitFromString("g"), progress.proteins)
        val fatQuantity = HKQuantity.quantityWithUnit(HKUnit.unitFromString("g"), progress.fats)
        val carbQuantity = HKQuantity.quantityWithUnit(HKUnit.unitFromString("g"), progress.carbohydrates)

        val samples = listOf(
            HKQuantitySample.quantitySampleWithType(energyType, energyQuantity, start, start),
            HKQuantitySample.quantitySampleWithType(proteinType, proteinQuantity, start, start),
            HKQuantitySample.quantitySampleWithType(fatType, fatQuantity, start, start),
            HKQuantitySample.quantitySampleWithType(carbType, carbQuantity, start, start)
        )

        store.saveObjects(samples) { success, error ->
            if (success) {
                deferred.complete(Result.success(Unit))
            } else {
                deferred.complete(
                    Result.failure(HealthDataException.WriteError(Exception(error?.localizedDescription)))
                )
            }
        }

        return deferred.await()
    }

    private fun getGrantedPermissions(): Set<String> {
        val store = healthStore ?: return emptySet()
        val granted = mutableSetOf<String>()

        // Check write permissions directly
        if (store.authorizationStatusForType(weightType) == HKAuthorizationStatusSharingAuthorized) {
            granted.add(HealthPermissionDataModel.WEIGHT_WRITE.name)
            // If we can write weight, we assume we can read it too (common UI behavior)
            granted.add(HealthPermissionDataModel.WEIGHT_READ.name)
        }

        val nutritionWriteAuthorized = listOf(energyType, proteinType, fatType, carbType).all {
            store.authorizationStatusForType(it) == HKAuthorizationStatusSharingAuthorized
        }
        if (nutritionWriteAuthorized) {
            granted.add(HealthPermissionDataModel.NUTRITION_WRITE.name)
            granted.add(HealthPermissionDataModel.NUTRITION_READ.name)
        }

        // For read-only types like steps, HealthKit doesn't allow checking status.
        // However, we can check if the authorization request has been made.
        // If it was made and success was reported, we'll assume the user intended to enable it
        // unless we want to implement a more complex tracking.
        // For now, let's check if the user has authorized AT LEAST one write permission
        // as a proxy for "HealthKit is enabled".
        if (granted.isNotEmpty()) {
            granted.add(HealthPermissionDataModel.STEPS_READ.name)
        }

        return granted
    }

    override suspend fun getPermissionStatuses(): List<HealthPermissionStatusDataModel> {
        val granted = getGrantedPermissions()
        val isAnyGranted = granted.isNotEmpty()

        return listOf(
            HealthPermissionStatusDataModel(
                id = "integrated",
                isGranted = isAnyGranted
            )
        )
    }
}

// Helper extensions for NSDate conversion
private fun NSDate.toKotlinInstant(): Instant = Instant.fromEpochMilliseconds(
    (timeIntervalSince1970 * 1000.0).toLong()
)
