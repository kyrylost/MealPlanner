package dev.stukalo.mealplanner.data.health.impl

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Energy
import androidx.health.connect.client.units.Mass
import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.exception.HealthDataException
import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.data.health.model.HealthPermissionStatusDataModel
import dev.stukalo.mealplanner.data.health.model.HealthServiceStatusDataModel
import dev.stukalo.mealplanner.data.health.model.NutritionHealthModel
import dev.stukalo.mealplanner.data.health.model.WeightHealthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toKotlinLocalDate
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Clock

/**
 * Android implementation of [HealthDataSource] using Health Connect API.
 *
 * @property context Android context.
 * @property clock Provider for current time to avoid static time calls.
 */
internal class HealthDataSourceImpl(private val context: Context, private val clock: Clock) : HealthDataSource {

    private val healthConnectClient by lazy {
        if (HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE) {
            HealthConnectClient.getOrCreate(context)
        } else {
            null
        }
    }

    private val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(NutritionRecord::class),
        HealthPermission.getWritePermission(WeightRecord::class),
        HealthPermission.getWritePermission(NutritionRecord::class)
    )

    override suspend fun isAvailable(): Boolean =
        HealthConnectClient.getSdkStatus(context) == HealthConnectClient.SDK_AVAILABLE

    override suspend fun getStatus(): HealthServiceStatusDataModel = when (HealthConnectClient.getSdkStatus(context)) {
        HealthConnectClient.SDK_AVAILABLE -> HealthServiceStatusDataModel.AVAILABLE
        HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> HealthServiceStatusDataModel.NOT_INSTALLED
        else -> HealthServiceStatusDataModel.NOT_SUPPORTED
    }

    override suspend fun hasPermissions(): Boolean {
        val client = healthConnectClient ?: return false
        val granted = client.permissionController.getGrantedPermissions()
        return granted.containsAll(permissions)
    }

    private suspend fun getGrantedPermissions(): Set<String> {
        val client = healthConnectClient ?: return emptySet()
        return client.permissionController.getGrantedPermissions()
    }

    override suspend fun getPermissionStatuses(): List<HealthPermissionStatusDataModel> {
        val granted = getGrantedPermissions()
        return listOf(
            HealthPermissionStatusDataModel(
                id = HealthDataSource.ID_STEPS,
                isGranted = granted.contains(getPermissionString(HealthPermissionDataModel.STEPS_READ))
            ),
            HealthPermissionStatusDataModel(
                id = HealthDataSource.ID_WEIGHT_READ,
                isGranted = granted.contains(getPermissionString(HealthPermissionDataModel.WEIGHT_READ))
            ),
            HealthPermissionStatusDataModel(
                id = HealthDataSource.ID_WEIGHT_WRITE,
                isGranted = granted.contains(getPermissionString(HealthPermissionDataModel.WEIGHT_WRITE))
            ),
            HealthPermissionStatusDataModel(
                id = HealthDataSource.ID_NUTRITION_READ,
                isGranted = granted.contains(getPermissionString(HealthPermissionDataModel.NUTRITION_READ))
            ),
            HealthPermissionStatusDataModel(
                id = HealthDataSource.ID_NUTRITION_WRITE,
                isGranted = granted.contains(getPermissionString(HealthPermissionDataModel.NUTRITION_WRITE))
            )
        )
    }

    private fun getPermissionString(type: HealthPermissionDataModel): String = when (type) {
        HealthPermissionDataModel.STEPS_READ -> HealthPermission.getReadPermission(StepsRecord::class)
        HealthPermissionDataModel.WEIGHT_READ -> HealthPermission.getReadPermission(WeightRecord::class)
        HealthPermissionDataModel.WEIGHT_WRITE -> HealthPermission.getWritePermission(WeightRecord::class)
        HealthPermissionDataModel.NUTRITION_READ -> HealthPermission.getReadPermission(NutritionRecord::class)
        HealthPermissionDataModel.NUTRITION_WRITE -> HealthPermission.getWritePermission(NutritionRecord::class)
    }

    override suspend fun requestPermissions(permissionId: String?): Result<Set<HealthPermissionDataModel>> {
        val typesToRequest = when (permissionId) {
            null -> HealthPermissionDataModel.entries.toSet()
            HealthDataSource.ID_STEPS -> setOf(HealthPermissionDataModel.STEPS_READ)
            HealthDataSource.ID_WEIGHT_READ -> setOf(HealthPermissionDataModel.WEIGHT_READ)
            HealthDataSource.ID_WEIGHT_WRITE -> setOf(HealthPermissionDataModel.WEIGHT_WRITE)
            HealthDataSource.ID_NUTRITION_READ -> setOf(HealthPermissionDataModel.NUTRITION_READ)
            HealthDataSource.ID_NUTRITION_WRITE -> setOf(HealthPermissionDataModel.NUTRITION_WRITE)
            else -> HealthPermissionDataModel.entries.toSet() // Should be "integrated" or similar, default to all
        }

        return Result.success(typesToRequest)
    }

    override fun getStepsAsFlow(date: LocalDate): Flow<Int> = flow {
        val client = healthConnectClient ?: return@flow emit(0)

        try {
            val startOfDay = Instant.ofEpochMilli(
                date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            )
            val endOfDay = Instant.ofEpochMilli(
                date.plus(1, DateTimeUnit.DAY)
                    .atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            )

            val response = client.aggregate(
                AggregateRequest(
                    metrics = setOf(StepsRecord.COUNT_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(startOfDay, endOfDay)
                )
            )
            emit(response[StepsRecord.COUNT_TOTAL]?.toInt() ?: 0)
        } catch (_: Exception) {
            emit(0)
        }
    }

    override suspend fun fetchWeightHistory(startTime: kotlin.time.Instant): Result<List<WeightHealthModel>> {
        val client = healthConnectClient ?: return Result.failure(HealthDataException.ServiceUnavailable())
        return try {
            val response = client.readRecords(
                ReadRecordsRequest(
                    recordType = WeightRecord::class,
                    timeRangeFilter = TimeRangeFilter.after(Instant.ofEpochMilli(startTime.toEpochMilliseconds()))
                )
            )
            val models = response.records.map { record ->
                WeightHealthModel(
                    date = record.time.atZone(ZoneId.systemDefault()).toLocalDate().toKotlinLocalDate(),
                    weight = record.weight.inKilograms
                )
            }
            Result.success(models)
        } catch (e: Exception) {
            Result.failure(HealthDataException.SyncError(e))
        }
    }

    override suspend fun fetchNutrientChanges(
        startTime: kotlin.time.Instant
    ): Result<Map<LocalDate, NutritionHealthModel>> {
        val client = healthConnectClient ?: return Result.failure(HealthDataException.ServiceUnavailable())
        return try {
            val response = client.readRecords(
                ReadRecordsRequest(
                    recordType = NutritionRecord::class,
                    timeRangeFilter = TimeRangeFilter.after(Instant.ofEpochMilli(startTime.toEpochMilliseconds()))
                )
            )

            val progressMap = response.records.groupBy { record ->
                record
                    .startTime
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .toKotlinLocalDate()
            }.mapValues { (date, records) ->
                NutritionHealthModel(
                    date = date,
                    calories = records.sumOf { it.energy?.inKilocalories ?: 0.0 },
                    proteins = records.sumOf { it.protein?.inGrams ?: 0.0 },
                    fats = records.sumOf { it.totalFat?.inGrams ?: 0.0 },
                    carbohydrates = records.sumOf { it.totalCarbohydrate?.inGrams ?: 0.0 }
                )
            }
            Result.success(progressMap)
        } catch (e: Exception) {
            Result.failure(HealthDataException.SyncError(e))
        }
    }

    override suspend fun writeWeight(weight: WeightHealthModel): Result<Unit> {
        val client = healthConnectClient ?: return Result.failure(HealthDataException.ServiceUnavailable())
        return try {
            val now = Instant.ofEpochMilli(clock.now().toEpochMilliseconds())
            val zoneOffset = ZoneId.systemDefault().rules.getOffset(now)
            val record = WeightRecord(
                time = Instant.ofEpochMilli(
                    weight
                        .date
                        .atStartOfDayIn(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                ),
                weight = Mass.kilograms(weight.weight),
                zoneOffset = zoneOffset,
                metadata = Metadata.manualEntry()
            )
            client.insertRecords(listOf(record))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(HealthDataException.WriteError(e))
        }
    }

    override suspend fun writeNutrition(date: LocalDate, progress: NutritionHealthModel): Result<Unit> {
        val client = healthConnectClient ?: return Result.failure(HealthDataException.ServiceUnavailable())
        return try {
            val now = Instant.ofEpochMilli(clock.now().toEpochMilliseconds())
            val zoneOffset = ZoneId.systemDefault().rules.getOffset(now)
            val record = NutritionRecord(
                startTime = Instant.ofEpochMilli(
                    date
                        .atStartOfDayIn(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                ),
                endTime = Instant.ofEpochMilli(
                    date
                        .plus(1, DateTimeUnit.DAY)
                        .atStartOfDayIn(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                ),
                energy = Energy.kilocalories(progress.calories),
                protein = Mass.grams(progress.proteins),
                totalFat = Mass.grams(progress.fats),
                totalCarbohydrate = Mass.grams(progress.carbohydrates),
                startZoneOffset = zoneOffset,
                endZoneOffset = zoneOffset,
                metadata = Metadata.manualEntry()
            )
            client.insertRecords(listOf(record))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(HealthDataException.WriteError(e))
        }
    }
}
