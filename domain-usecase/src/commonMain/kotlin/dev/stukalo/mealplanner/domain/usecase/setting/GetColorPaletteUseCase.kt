package dev.stukalo.mealplanner.domain.usecase.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Use case to retrieve the current color palette preference.
 */
interface GetColorPaletteUseCase {
    /**
     * Returns a [Flow] that emits the current [ColorPaletteDomainModel].
     */
    operator fun invoke(): Flow<ColorPaletteDomainModel>
}
