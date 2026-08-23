package dev.stukalo.mealplanner.presentation.feature.settings.di

import dev.stukalo.mealplanner.presentation.feature.settings.core.mapper.HealthPermissionMapper
import dev.stukalo.mealplanner.presentation.feature.settings.screen.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule =
    module {
        singleOf(::HealthPermissionMapper)
        viewModel {
            SettingsViewModel(
                setColorPaletteUseCase = get(),
                getColorPaletteUseCase = get(),
                setThemeModeUseCase = get(),
                getThemeModeUseCase = get(),
                setLocaleUseCase = get(),
                getLocaleUseCase = get(),
                saveUserDataUseCase = get(),
                getUserUseCase = get(),
                calculateDailyNormUseCase = get(),
                saveDailyNormUseCase = get(),
                validateWeightUseCase = get(),
                validateHeightUseCase = get(),
                validateStepsTargetUseCase = get(),
                getMealRemindersEnabledUseCase = get(),
                setMealRemindersEnabledUseCase = get(),
                syncMealRemindersUseCase = get(),
                hasNotificationPermissionUseCase = get(),
                getHealthServiceStatusUseCase = get(),
                getHealthPermissionStatusUseCase = get(),
                requestHealthPermissionsUseCase = get(),
                openHealthSettingsUseCase = get(),
                installHealthConnectUseCase = get(),
                applyLocaleUseCase = get(),
                getSystemLocaleUseCase = get(),
                healthPermissionMapper = get()
            )
        }
    }
