package dev.stukalo.mealplanner.domain.usecase.setting

import kotlinx.coroutines.flow.Flow

/**
 * Interface for getting the meal reminders enabled state.
 */
interface GetMealRemindersEnabledUseCase {
    /**
     * Returns a flow of the meal reminders enabled state.
     */
    operator fun invoke(): Flow<Boolean>
}
