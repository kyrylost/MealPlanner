package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.GetMealRemindersEnabledUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [GetMealRemindersEnabledUseCase].
 */
class GetMealRemindersEnabledUseCaseImpl(private val repository: SettingsRepository) : GetMealRemindersEnabledUseCase {
    override fun invoke(): Flow<Boolean> = repository.isMealRemindersEnabled()
}
