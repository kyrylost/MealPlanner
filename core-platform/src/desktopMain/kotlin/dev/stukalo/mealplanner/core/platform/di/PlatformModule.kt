package dev.stukalo.mealplanner.core.platform.di

import dev.stukalo.mealplanner.core.platform.DesktopHealthManager
import dev.stukalo.mealplanner.core.platform.DesktopLocaleManager
import dev.stukalo.mealplanner.core.platform.HealthManager
import dev.stukalo.mealplanner.core.platform.LocaleManager
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
}
