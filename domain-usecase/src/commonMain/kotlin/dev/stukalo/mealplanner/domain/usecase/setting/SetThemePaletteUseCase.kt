package dev.stukalo.mealplanner.domain.usecase.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel

interface SetThemePaletteUseCase {
    suspend operator fun invoke(palette: ColorPaletteDomainModel)
}
