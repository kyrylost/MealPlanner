package dev.stukalo.mealplanner.domain.usecase.slot

/**
 * Interface for synchronizing meal reminders with the system notification scheduler.
 */
interface SyncMealRemindersUseCase {
    /**
     * Reschedules all meal reminders based on current settings and meal schedule.
     */
    suspend operator fun invoke(): Result<Unit>
}
