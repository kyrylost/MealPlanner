package dev.stukalo.mealplanner.data.preferences.settings.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.stukalo.mealplanner.data.preferences.settings.SettingsPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferencesDataSourceImpl(private val dataStore: DataStore<Preferences>) :
    SettingsPreferencesDataSource {
    private object PreferencesKeys {
        val COLOR_PALETTE = stringPreferencesKey("color_palette")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val LOCALE = stringPreferencesKey("locale")
        val LAST_HEALTH_SYNC_TIME = longPreferencesKey("last_health_sync_time")
    }

    override fun getColorPaletteName(): Flow<String?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.COLOR_PALETTE] }

    override suspend fun setColorPaletteName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.COLOR_PALETTE] = name
        }
    }

    override fun getThemeModeName(): Flow<String?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.THEME_MODE] }

    override suspend fun setThemeModeName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = name
        }
    }

    override fun getLocale(): Flow<String?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.LOCALE] }

    override suspend fun setLocale(locale: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCALE] = locale
        }
    }

    override fun getLastHealthSyncTime(): Flow<Long?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.LAST_HEALTH_SYNC_TIME] }

    override suspend fun setLastHealthSyncTime(time: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_HEALTH_SYNC_TIME] = time
        }
    }
}
