package dev.stukalo.mealplanner.platform.di

import dev.stukalo.mealplanner.domain.service.HealthManager
import dev.stukalo.mealplanner.domain.service.LocaleManager
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import dev.stukalo.mealplanner.platform.DesktopHealthManager
import dev.stukalo.mealplanner.platform.DesktopLocaleManager
import dev.stukalo.mealplanner.platform.DesktopNotificationScheduler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Desktop implementation of [platformModule].
 */
actual val platformModule: Module = module {
    singleOf(::DesktopHealthManager) bind HealthManager::class
    singleOf(::DesktopLocaleManager) bind LocaleManager::class
    single(createdAtStart = true) { DesktopNotificationScheduler(get()) } bind NotificationScheduler::class
}
