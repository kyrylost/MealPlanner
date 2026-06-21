package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.GetThemePaletteUseCase
import kotlinx.coroutines.flow.Flow

class GetThemePaletteUseCaseImpl(
    private val settingsRepository: SettingsRepository
) : GetThemePaletteUseCase {
    override fun invoke(): Flow<ColorPaletteDomainModel> = settingsRepository.getThemePalette()
}
