package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemeModeUseCase

/**
 * Implementation of [SetThemeModeUseCase] that updates preference in [SettingsRepository].
 */
class SetThemeModeUseCaseImpl(private val settingsRepository: SettingsRepository) : SetThemeModeUseCase {
    override suspend fun invoke(mode: ThemeModeDomainModel) {
        settingsRepository.setThemeMode(mode)
    }
}
