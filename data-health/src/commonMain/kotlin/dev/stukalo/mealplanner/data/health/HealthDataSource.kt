package dev.stukalo.mealplanner.data.health

import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.data.health.model.HealthPermissionStatusDataModel
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
    companion object {
        /** ID for step count permission group. */
        const val ID_STEPS = "steps"

        /** ID for weight read permission group. */
        const val ID_WEIGHT_READ = "weight_read"

        /** ID for weight write permission group. */
        const val ID_WEIGHT_WRITE = "weight_write"

        /** ID for nutrition read permission group. */
        const val ID_NUTRITION_READ = "nutrition_read"

        /** ID for nutrition write permission group. */
        const val ID_NUTRITION_WRITE = "nutrition_write"

        /** ID for integrated health services (e.g. Apple Health). */
        const val ID_INTEGRATED = "integrated"
    }

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
     * @param permissionId Optional identifier for a specific permission group. If null, requests all.
     * @return Result containing the set of newly granted permission types.
     */
    suspend fun requestPermissions(permissionId: String? = null): Result<Set<HealthPermissionDataModel>>

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
     * Returns a list of health permission statuses grouped for the current platform.
     */
    suspend fun getPermissionStatuses(): List<HealthPermissionStatusDataModel>
}
