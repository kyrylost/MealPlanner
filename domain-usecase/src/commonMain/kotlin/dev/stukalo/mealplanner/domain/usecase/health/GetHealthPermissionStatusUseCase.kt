package dev.stukalo.mealplanner.domain.usecase.health

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import kotlinx.coroutines.flow.Flow

/**
 * Use case for retrieving the list of health permission statuses available on the current platform.
 */
interface GetHealthPermissionStatusUseCase {
    /**
     * Returns a flow containing the list of [HealthPermissionStatus]es.
     */
    operator fun invoke(): Flow<List<HealthPermissionStatus>>
}
