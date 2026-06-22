package dev.stukalo.mealplanner.domain.usecase.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import kotlinx.coroutines.flow.Flow

interface GetColorPaletteUseCase {
    operator fun invoke(): Flow<ColorPaletteDomainModel>
}
