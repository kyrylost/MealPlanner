package dev.stukalo.mealplanner.data.preferences.settings.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.stukalo.mealplanner.data.preferences.settings.SettingsPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreferencesDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsPreferencesDataSource {

    private object PreferencesKeys {
        val THEME_PALETTE = stringPreferencesKey("theme_palette")
        val LOCALE = stringPreferencesKey("locale")
    }

    override fun getThemePaletteName(): Flow<String?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.THEME_PALETTE] }

    override suspend fun setThemePaletteName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_PALETTE] = name
        }
    }

    override fun getLocale(): Flow<String?> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.LOCALE] }

    override suspend fun setLocale(locale: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOCALE] = locale
        }
    }
}
