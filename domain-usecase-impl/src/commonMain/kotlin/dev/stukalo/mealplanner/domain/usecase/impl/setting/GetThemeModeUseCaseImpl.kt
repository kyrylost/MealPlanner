package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.GetThemeModeUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [GetThemeModeUseCase] that retrieves preference from [SettingsRepository].
 */
class GetThemeModeUseCaseImpl(private val settingsRepository: SettingsRepository) : GetThemeModeUseCase {
    override fun invoke(): Flow<ThemeModeDomainModel> = settingsRepository.getThemeMode()
}
