package dev.stukalo.mealplanner.platform.di

import dev.stukalo.mealplanner.domain.service.HealthManager
import dev.stukalo.mealplanner.domain.service.LocaleManager
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import dev.stukalo.mealplanner.platform.AndroidHealthManager
import dev.stukalo.mealplanner.platform.AndroidLocaleManager
import dev.stukalo.mealplanner.platform.AndroidNotificationScheduler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Android implementation of [platformModule].
 */
actual val platformModule: Module = module {
    singleOf(::AndroidHealthManager) bind HealthManager::class
    singleOf(::AndroidLocaleManager) bind LocaleManager::class
    singleOf(::AndroidNotificationScheduler) bind NotificationScheduler::class
}
