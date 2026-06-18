package dev.stukalo.mealplanner.data.database.room

import androidx.room.Room
import androidx.room.RoomDatabase
import dev.stukalo.mealplanner.data.database.AppDatabase
import dev.stukalo.mealplanner.data.database.AppDatabaseConstructor
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
        factory = { AppDatabaseConstructor.initialize() }
    ).fallbackToDestructiveMigration(
        dropAllTables = true
    )
}