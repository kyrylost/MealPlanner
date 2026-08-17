package dev.stukalo.mealplanner.core.platform.di

import dev.stukalo.mealplanner.core.platform.AndroidHealthManager
import dev.stukalo.mealplanner.core.platform.AndroidLocaleManager
import dev.stukalo.mealplanner.core.platform.HealthManager
import dev.stukalo.mealplanner.core.platform.LocaleManager
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
}
