package dev.stukalo.mealplanner.presentation.feature.settings.di

import dev.stukalo.mealplanner.presentation.feature.settings.core.mapper.HealthPermissionMapper
import dev.stukalo.mealplanner.presentation.feature.settings.screen.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule =
    module {
        singleOf(::HealthPermissionMapper)
        viewModelOf(::SettingsViewModel)
    }
