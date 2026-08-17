package dev.stukalo.mealplanner.data.health.impl

import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.exception.HealthDataException
import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.data.health.model.HealthServiceStatusDataModel
import dev.stukalo.mealplanner.data.health.model.NutritionHealthModel
import dev.stukalo.mealplanner.data.health.model.WeightHealthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

/**
 * Implementation of [HealthDataSource] for platforms where health tracking is not available.
 */
internal class UnavailableHealthDataSourceImpl : HealthDataSource {
    override suspend fun isAvailable(): Boolean = false
    override suspend fun getStatus(): HealthServiceStatusDataModel = HealthServiceStatusDataModel.NOT_SUPPORTED
    override suspend fun hasPermissions(): Boolean = false
    override suspend fun getGrantedPermissions(): Set<String> = emptySet()

    override fun getPermissionString(type: HealthPermissionDataModel): String = ""

    override suspend fun requestPermissions(): Result<Boolean> = Result.success(false)
    override fun getStepsAsFlow(date: LocalDate): Flow<Int> = flowOf(0)

    override suspend fun fetchWeightHistory(startTime: Instant): Result<List<WeightHealthModel>> =
        Result.failure(HealthDataException.ServiceUnavailable())

    override suspend fun fetchNutrientChanges(startTime: Instant): Result<Map<LocalDate, NutritionHealthModel>> =
        Result.failure(HealthDataException.ServiceUnavailable())

    override suspend fun writeWeight(weight: WeightHealthModel): Result<Unit> =
        Result.failure(HealthDataException.ServiceUnavailable())

    override suspend fun writeNutrition(date: LocalDate, progress: NutritionHealthModel): Result<Unit> =
        Result.failure(HealthDataException.ServiceUnavailable())

    override fun getPermissionStrings(): Set<String> = emptySet()
}
