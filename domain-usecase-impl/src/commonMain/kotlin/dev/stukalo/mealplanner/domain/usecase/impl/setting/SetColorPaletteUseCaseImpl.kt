package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.SetColorPaletteUseCase

/**
 * Implementation of [SetColorPaletteUseCase] that updates preference in [SettingsRepository].
 */
class SetColorPaletteUseCaseImpl(private val settingsRepository: SettingsRepository) : SetColorPaletteUseCase {
    override suspend fun invoke(palette: ColorPaletteDomainModel) {
        settingsRepository.setColorPalette(palette)
    }
}
