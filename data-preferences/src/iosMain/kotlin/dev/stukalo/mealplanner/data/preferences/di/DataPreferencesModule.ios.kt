package dev.stukalo.mealplanner.data.preferences.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.stukalo.mealplanner.data.preferences.core.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataPreferencesModule: Module = module {
    single<DataStore<Preferences>> { createDataStore() }
}
