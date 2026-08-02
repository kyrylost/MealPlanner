package dev.stukalo.mealplanner.data.repository.impl.setting

import dev.stukalo.mealplanner.data.preferences.settings.SettingsPreferencesDataSource
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val dataSource: SettingsPreferencesDataSource) : SettingsRepository {
    override fun getColorPalette(): Flow<ColorPaletteDomainModel> = dataSource
        .getColorPaletteName()
        .map { name ->
            if (name == null) return@map ColorPaletteDomainModel.ORANGE
            try {
                ColorPaletteDomainModel.valueOf(name)
            } catch (_: Exception) {
                ColorPaletteDomainModel.ORANGE
            }
        }

    override suspend fun setColorPalette(palette: ColorPaletteDomainModel) {
        dataSource.setColorPaletteName(palette.name)
    }

    override fun getLocale(): Flow<String> = dataSource
        .getLocale()
        .map { it ?: "en" } // Default locale

    override suspend fun setLocale(locale: String) {
        dataSource.setLocale(locale)
    }
}
