package dev.stukalo.mealplanner.platform.di

import dev.stukalo.mealplanner.domain.service.HealthManager
import dev.stukalo.mealplanner.domain.service.LocaleManager
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import dev.stukalo.mealplanner.platform.IosHealthManager
import dev.stukalo.mealplanner.platform.IosLocaleManager
import dev.stukalo.mealplanner.platform.IosNotificationScheduler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * iOS implementation of [platformModule].
 */
actual val platformModule: Module = module {
    singleOf(::IosHealthManager) bind HealthManager::class
    singleOf(::IosLocaleManager) bind LocaleManager::class
    singleOf(::IosNotificationScheduler) bind NotificationScheduler::class
}
