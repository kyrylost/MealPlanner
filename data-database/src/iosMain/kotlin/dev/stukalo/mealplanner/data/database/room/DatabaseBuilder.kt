package dev.stukalo.mealplanner.data.database.room

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.stukalo.mealplanner.data.database.AppDatabase
import dev.stukalo.mealplanner.data.database.AppDatabaseConstructor
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/my_room.db"
    return Room
        .databaseBuilder<AppDatabase>(
            name = dbFilePath,
            factory = { AppDatabaseConstructor.initialize() }
        ).fallbackToDestructiveMigration(
            dropAllTables = true
        )
}
