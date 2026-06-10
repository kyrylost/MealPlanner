package dev.stukalo.mealplanner.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.stukalo.mealplanner.data.database.dao.UserDao
import dev.stukalo.mealplanner.data.database.model.UserDatabaseModel

@Database(entities = [UserDatabaseModel::class], version = 2)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun getDao(): UserDao

}

fun getUserDao(appDatabase: AppDatabase): UserDao = appDatabase.getDao()
