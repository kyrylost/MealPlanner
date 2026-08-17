package dev.stukalo.mealplanner.data.health.impl.di

import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.impl.HealthDataSourceImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val healthDataModule: Module = module {
    singleOf(::HealthDataSourceImpl) bind HealthDataSource::class
}
