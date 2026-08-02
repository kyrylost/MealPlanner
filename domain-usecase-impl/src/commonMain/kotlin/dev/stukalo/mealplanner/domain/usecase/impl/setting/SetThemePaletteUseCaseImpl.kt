package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemePaletteUseCase

class SetThemePaletteUseCaseImpl(private val settingsRepository: SettingsRepository) : SetThemePaletteUseCase {
    override suspend fun invoke(palette: ColorPaletteDomainModel) {
        settingsRepository.setColorPalette(palette)
    }
}
