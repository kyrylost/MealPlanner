package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.IsOnboardingShownUseCase
import kotlinx.coroutines.flow.Flow

internal class IsOnboardingShownUseCaseImpl(private val repository: SettingsRepository) : IsOnboardingShownUseCase {
    override fun invoke(): Flow<Boolean> = repository.isOnboardingShown()
}
