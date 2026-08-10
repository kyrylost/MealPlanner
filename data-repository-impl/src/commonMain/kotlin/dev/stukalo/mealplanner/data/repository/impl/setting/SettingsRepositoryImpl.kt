package dev.stukalo.mealplanner.data.repository.impl.setting

import dev.stukalo.mealplanner.data.preferences.settings.SettingsPreferencesDataSource
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [SettingsRepository].
 * Manages user preferences such as theme palette and locale.
 */
class SettingsRepositoryImpl(private val dataSource: SettingsPreferencesDataSource) : SettingsRepository {
    /**
     * Gets the current color palette from data source.
     */
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

    /**
     * Sets the color palette in data source.
     */
    override suspend fun setColorPalette(palette: ColorPaletteDomainModel) {
        dataSource.setColorPaletteName(palette.name)
    }

    override fun getThemeMode(): Flow<ThemeModeDomainModel> = dataSource
        .getThemeModeName()
        .map { name ->
            if (name == null) return@map ThemeModeDomainModel.AUTO
            try {
                ThemeModeDomainModel.valueOf(name)
            } catch (_: Exception) {
                ThemeModeDomainModel.AUTO
            }
        }

    override suspend fun setThemeMode(mode: ThemeModeDomainModel) {
        dataSource.setThemeModeName(mode.name)
    }

    /**
     * Gets the current locale from data source.
     */
    override fun getLocale(): Flow<String?> = dataSource.getLocale()

    /**
     * Sets the locale in data source.
     */
    override suspend fun setLocale(locale: String) {
        dataSource.setLocale(locale)
    }
}
