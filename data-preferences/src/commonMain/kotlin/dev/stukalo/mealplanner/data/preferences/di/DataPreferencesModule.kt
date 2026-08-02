package dev.stukalo.mealplanner.data.preferences.di

import dev.stukalo.mealplanner.data.preferences.settings.SettingsPreferencesDataSource
import dev.stukalo.mealplanner.data.preferences.settings.impl.SettingsPreferencesDataSourceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataPreferencesModule: Module =
    module {
        singleOf(::SettingsPreferencesDataSourceImpl) bind SettingsPreferencesDataSource::class
    }

expect val platformDataPreferencesModule: Module
