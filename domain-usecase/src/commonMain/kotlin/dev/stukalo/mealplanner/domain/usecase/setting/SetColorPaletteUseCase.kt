package dev.stukalo.mealplanner.domain.usecase.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel

/**
 * Use case to update the color palette preference.
 */
interface SetColorPaletteUseCase {
    /**
     * Sets the application color palette.
     *
     * @param palette The new [ColorPaletteDomainModel] to apply.
     */
    suspend operator fun invoke(palette: ColorPaletteDomainModel)
}
