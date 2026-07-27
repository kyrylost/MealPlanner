package dev.stukalo.mealplanner.data.database.di

import dev.stukalo.mealplanner.data.database.getDailyNormDao
import dev.stukalo.mealplanner.data.database.getDailyProgressDao
import dev.stukalo.mealplanner.data.database.getMealSlotDao
import dev.stukalo.mealplanner.data.database.getRoomDatabase
import dev.stukalo.mealplanner.data.database.getUserDao
import dev.stukalo.mealplanner.data.database.source.norm.DailyNormDatabaseSource
import dev.stukalo.mealplanner.data.database.source.progress.DailyProgressDatabaseSource
import dev.stukalo.mealplanner.data.database.source.slot.MealSlotDatabaseSource
import dev.stukalo.mealplanner.data.database.source.user.UserDatabaseSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect val databaseBuilderModule: Module

val databaseModule = module {
    singleOf(::getRoomDatabase)
    singleOf(::getUserDao)
    singleOf(::getDailyNormDao)
    singleOf(::getDailyProgressDao)
    singleOf(::getMealSlotDao)
    singleOf(::UserDatabaseSource)
    singleOf(::DailyNormDatabaseSource)
    singleOf(::DailyProgressDatabaseSource)
    singleOf(::MealSlotDatabaseSource)
}
