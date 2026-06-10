package dev.stukalo.mealplanner.data.database.di

import dev.stukalo.mealplanner.data.database.getRoomDatabase
import dev.stukalo.mealplanner.data.database.getUserDao
import dev.stukalo.mealplanner.data.database.source.UserDatabaseSource
import org.koin.core.module.Module
import org.koin.dsl.module

expect val databaseBuilderModule: Module

val databaseModule = module {
    single { getRoomDatabase(get()) }
    single { getUserDao(get()) }
    single { UserDatabaseSource(get()) }
}