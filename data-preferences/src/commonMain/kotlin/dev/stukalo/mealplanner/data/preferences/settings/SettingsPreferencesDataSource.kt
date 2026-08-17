package dev.stukalo.mealplanner.data.preferences.settings

import kotlinx.coroutines.flow.Flow

interface SettingsPreferencesDataSource {
    fun getColorPaletteName(): Flow<String?>

    suspend fun setColorPaletteName(name: String)

    fun getThemeModeName(): Flow<String?>

    suspend fun setThemeModeName(name: String)

    fun getLocale(): Flow<String?>

    suspend fun setLocale(locale: String)

    fun getLastHealthSyncTime(): Flow<Long?>

    suspend fun setLastHealthSyncTime(time: Long)
}
