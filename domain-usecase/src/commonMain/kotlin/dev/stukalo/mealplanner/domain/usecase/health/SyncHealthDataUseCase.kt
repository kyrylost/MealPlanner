package dev.stukalo.mealplanner.domain.usecase.health

/**
 * Use case for synchronizing all health data (weight, nutrients) from platform health services.
 */
interface SyncHealthDataUseCase {

    /**
     * Executes the synchronization.
     */
    suspend operator fun invoke(): Result<Unit>
}
