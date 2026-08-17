package dev.stukalo.mealplanner.core.platform.di

import dev.stukalo.mealplanner.core.platform.HealthManager
import dev.stukalo.mealplanner.core.platform.IosHealthManager
import dev.stukalo.mealplanner.core.platform.IosLocaleManager
import dev.stukalo.mealplanner.core.platform.LocaleManager
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
}
