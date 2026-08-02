package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getColorPalette(): Flow<ColorPaletteDomainModel>

    suspend fun setColorPalette(palette: ColorPaletteDomainModel)

    fun getLocale(): Flow<String>

    suspend fun setLocale(locale: String)
}
