package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

/**
 * Interface for health data synchronization.
 */
interface HealthRepository {
    /**
     * Checks if health tracking is available on the current platform.
     */
    suspend fun isAvailable(): Boolean

    /**
     * Returns the current status of the health service.
     */
    suspend fun getStatus(): HealthServiceStatus

    /**
     * Checks if the app has all necessary permissions.
     */
    suspend fun hasPermissions(): Boolean

    /**
     * Returns a list of health permission statuses grouped for the current platform.
     */
    suspend fun getPermissionStatuses(): List<HealthPermissionStatus>

    /**
     * Requests necessary permissions for health data access.
     *
     * @param group Optional identifier for a specific permission group. If null, requests all.
     * @return Result containing the set of newly granted permission types.
     */
    suspend fun requestPermissions(group: HealthPermissionGroup? = null): Result<Set<HealthPermissionType>>

    /**
     * Returns a flow of step count for the given date.
     */
    fun getStepsAsFlow(date: LocalDate): Flow<Int>

    /**
     * Synchronizes all health data since last sync.
     */
    suspend fun syncAllData(): Result<Unit>

    /**
     * Writes weight to health services.
     */
    suspend fun writeWeight(weight: WeightHistoryDomainModel): Result<Unit>

    /**
     * Writes nutrition progress to health services.
     */
    suspend fun writeNutrition(date: LocalDate, progress: DailyProgressDomainModel): Result<Unit>
}
