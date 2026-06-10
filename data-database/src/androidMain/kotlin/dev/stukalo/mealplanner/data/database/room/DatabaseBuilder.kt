package dev.stukalo.mealplanner.data.database.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.stukalo.mealplanner.data.database.AppDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<AppDatabase>(
      context = appContext,
      name = dbFile.absolutePath
    ).fallbackToDestructiveMigration(
      dropAllTables = true
    )
}