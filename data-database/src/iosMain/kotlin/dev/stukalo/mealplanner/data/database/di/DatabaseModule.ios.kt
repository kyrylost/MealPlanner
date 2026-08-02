package dev.stukalo.mealplanner.data.database.di

import androidx.room.RoomDatabase
import dev.stukalo.mealplanner.data.database.AppDatabase
import dev.stukalo.mealplanner.data.database.room.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseBuilderModule: Module =
    module {
        single<RoomDatabase.Builder<AppDatabase>> {
            getDatabaseBuilder()
        }
    }
