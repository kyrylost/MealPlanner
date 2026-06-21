package dev.stukalo.mealplanner.data.preferences.settings

import kotlinx.coroutines.flow.Flow

interface SettingsPreferencesDataSource {
    fun getThemePaletteName(): Flow<String?>
    suspend fun setThemePaletteName(name: String)
    fun getLocale(): Flow<String?>
    suspend fun setLocale(locale: String)
}
