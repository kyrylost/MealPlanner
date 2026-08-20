package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.SetOnboardingShownUseCase

internal class SetOnboardingShownUseCaseImpl(private val repository: SettingsRepository) : SetOnboardingShownUseCase {
    override suspend fun invoke(shown: Boolean) {
        repository.setOnboardingShown(shown)
    }
}
