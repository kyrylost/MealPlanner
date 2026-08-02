package dev.stukalo.mealplanner.presentation.feature.settings.di

import dev.stukalo.mealplanner.presentation.feature.settings.screen.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule =
    module {
        viewModelOf(::SettingsViewModel)
    }
