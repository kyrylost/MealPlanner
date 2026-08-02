package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.SetLocaleUseCase

class SetLocaleUseCaseImpl(private val settingsRepository: SettingsRepository) : SetLocaleUseCase {
    override suspend fun invoke(locale: String) {
        settingsRepository.setLocale(locale)
    }
}
