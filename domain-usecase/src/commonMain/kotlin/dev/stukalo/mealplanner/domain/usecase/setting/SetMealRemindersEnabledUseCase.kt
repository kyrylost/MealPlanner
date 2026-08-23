package dev.stukalo.mealplanner.domain.usecase.setting

/**
 * Interface for setting the meal reminders enabled state.
 */
interface SetMealRemindersEnabledUseCase {
    /**
     * Sets the meal reminders enabled state.
     * @param enabled Whether meal reminders should be enabled.
     */
    suspend operator fun invoke(enabled: Boolean)
}
