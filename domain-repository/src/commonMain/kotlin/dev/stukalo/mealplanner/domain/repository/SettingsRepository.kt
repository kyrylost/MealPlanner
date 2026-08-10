package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getColorPalette(): Flow<ColorPaletteDomainModel>

    suspend fun setColorPalette(palette: ColorPaletteDomainModel)

    fun getThemeMode(): Flow<ThemeModeDomainModel>

    suspend fun setThemeMode(mode: ThemeModeDomainModel)

    fun getLocale(): Flow<String?>

    suspend fun setLocale(locale: String)
}
