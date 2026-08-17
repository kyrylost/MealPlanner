package dev.stukalo.mealplanner.domain.usecase.health

import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus

/**
 * Use case to get the detailed status of the health service.
 * Use this for UI logic to provide specific feedback to the user
 * (e.g., showing an installation button or an "unsupported device" message).
 */
interface GetHealthServiceStatusUseCase {
    /**
     * Executes the use case.
     * @return The current [HealthServiceStatus] representing the availability state.
     */
    suspend operator fun invoke(): HealthServiceStatus
}
