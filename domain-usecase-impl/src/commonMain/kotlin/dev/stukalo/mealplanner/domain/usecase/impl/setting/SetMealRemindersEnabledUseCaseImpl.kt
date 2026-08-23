package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.SetMealRemindersEnabledUseCase

/**
 * Implementation of [SetMealRemindersEnabledUseCase].
 */
class SetMealRemindersEnabledUseCaseImpl(private val repository: SettingsRepository) : SetMealRemindersEnabledUseCase {
    override suspend fun invoke(enabled: Boolean) {
        repository.setMealRemindersEnabled(enabled)
    }
}
