package dev.stukalo.mealplanner.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.stukalo.mealplanner.data.database.dao.norm.DailyNormDao
import dev.stukalo.mealplanner.data.database.dao.progress.DailyProgressDao
import dev.stukalo.mealplanner.data.database.dao.user.UserDao
import dev.stukalo.mealplanner.data.database.model.converter.DateConverter
import dev.stukalo.mealplanner.data.database.model.norm.DailyNormDatabaseModel
import dev.stukalo.mealplanner.data.database.model.progress.DailyProgressDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel

@Database(
    entities = [
        UserDatabaseModel::class,
        DailyNormDatabaseModel::class,
        DailyProgressDatabaseModel::class
    ],
    version = 4
)
@TypeConverters(DateConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getDailyNormDao(): DailyNormDao
    abstract fun getDailyProgressDao(): DailyProgressDao
}

fun getUserDao(appDatabase: AppDatabase): UserDao = appDatabase.getUserDao()
fun getDailyNormDao(appDatabase: AppDatabase): DailyNormDao = appDatabase.getDailyNormDao()
fun getDailyProgressDao(appDatabase: AppDatabase): DailyProgressDao = appDatabase.getDailyProgressDao()
