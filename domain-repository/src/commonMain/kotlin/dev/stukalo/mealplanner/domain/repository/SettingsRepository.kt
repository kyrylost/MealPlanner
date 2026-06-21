package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getThemePalette(): Flow<ColorPaletteDomainModel>
    suspend fun setThemePalette(palette: ColorPaletteDomainModel)
    fun getLocale(): Flow<String>
    suspend fun setLocale(locale: String)
}
