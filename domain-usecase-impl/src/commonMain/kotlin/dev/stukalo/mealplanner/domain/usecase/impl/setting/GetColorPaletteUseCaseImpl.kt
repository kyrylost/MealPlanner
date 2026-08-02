package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import kotlinx.coroutines.flow.Flow

class GetColorPaletteUseCaseImpl(private val settingsRepository: SettingsRepository) : GetColorPaletteUseCase {
    override fun invoke(): Flow<ColorPaletteDomainModel> = settingsRepository.getColorPalette()
}
