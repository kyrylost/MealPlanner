package dev.stukalo.mealplanner.data.health

import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.data.health.model.HealthServiceStatusDataModel
import dev.stukalo.mealplanner.data.health.model.NutritionHealthModel
import dev.stukalo.mealplanner.data.health.model.WeightHealthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

/**
 * Data source for interacting with platform-specific health services (e.g., Health Connect on Android).
 */
interface HealthDataSource {
    /**
     * Checks if health tracking is available on the current device.
     */
    suspend fun isAvailable(): Boolean

    /**
     * Returns the current status of the health service.
     */
    suspend fun getStatus(): HealthServiceStatusDataModel

    /**
     * Checks if the app has been granted the necessary health permissions.
     */
    suspend fun hasPermissions(): Boolean

    /**
     * Requests the necessary health permissions.
     *
     * @return Result containing true if permissions were granted, false otherwise.
     */
    suspend fun requestPermissions(): Result<Boolean>

    /**
     * Returns a flow of step count for the given [date].
     */
    fun getStepsAsFlow(date: LocalDate): Flow<Int>

    /**
     * Fetches weight history since [startTime].
     */
    suspend fun fetchWeightHistory(startTime: Instant): Result<List<WeightHealthModel>>

    /**
     * Fetches nutrition changes since [startTime].
     */
    suspend fun fetchNutrientChanges(startTime: Instant): Result<Map<LocalDate, NutritionHealthModel>>

    /**
     * Writes a weight entry to health services.
     */
    suspend fun writeWeight(weight: WeightHealthModel): Result<Unit>

    /**
     * Writes nutrition progress for a specific [date] to health services.
     */
    suspend fun writeNutrition(date: LocalDate, progress: NutritionHealthModel): Result<Unit>

    /**
     * Returns a set of currently granted platform-specific permission strings.
     */
    suspend fun getGrantedPermissions(): Set<String>

    /**
     * Maps a [HealthPermissionDataModel] to a platform-specific permission string.
     */
    fun getPermissionString(type: HealthPermissionDataModel): String

    /**
     * Returns a set of all required platform-specific permission strings.
     */
    fun getPermissionStrings(): Set<String>
}
