package dev.stukalo.mealplanner.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.stukalo.mealplanner.data.database.dao.norm.DailyNormDao
import dev.stukalo.mealplanner.data.database.dao.progress.DailyProgressDao
import dev.stukalo.mealplanner.data.database.dao.slot.MealSlotDao
import dev.stukalo.mealplanner.data.database.dao.statistics.WeightHistoryDao
import dev.stukalo.mealplanner.data.database.dao.user.UserDao
import dev.stukalo.mealplanner.data.database.model.converter.DateConverter
import dev.stukalo.mealplanner.data.database.model.converter.MealTypeConverter
import dev.stukalo.mealplanner.data.database.model.converter.TimeConverter
import dev.stukalo.mealplanner.data.database.model.norm.DailyNormDatabaseModel
import dev.stukalo.mealplanner.data.database.model.progress.DailyProgressDatabaseModel
import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import dev.stukalo.mealplanner.data.database.model.statistics.WeightHistoryDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel

/**
 * Main database for the application.
 * Contains tables for users, daily norms, progress, and statistics.
 */
@Database(
    entities = [
        UserDatabaseModel::class,
        DailyNormDatabaseModel::class,
        DailyProgressDatabaseModel::class,
        MealSlotDatabaseModel::class,
        WeightHistoryDatabaseModel::class
    ],
    version = 10
)
@TypeConverters(DateConverter::class, TimeConverter::class, MealTypeConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    abstract fun getDailyNormDao(): DailyNormDao

    abstract fun getDailyProgressDao(): DailyProgressDao

    abstract fun getMealSlotDao(): MealSlotDao

    abstract fun getWeightHistoryDao(): WeightHistoryDao
}

/**
 * Provides an instance of [AppDatabase].
 *
 * @param builder The Room builder to use.
 * @return The constructed database instance.
 */
fun getRoomDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .fallbackToDestructiveMigration(true)
    .build()

/**
 * Convenience function to get [UserDao] from [AppDatabase].
 */
fun getUserDao(appDatabase: AppDatabase): UserDao = appDatabase.getUserDao()

/**
 * Convenience function to get [DailyNormDao] from [AppDatabase].
 */
fun getDailyNormDao(appDatabase: AppDatabase): DailyNormDao = appDatabase.getDailyNormDao()

/**
 * Convenience function to get [DailyProgressDao] from [AppDatabase].
 */
fun getDailyProgressDao(appDatabase: AppDatabase): DailyProgressDao = appDatabase.getDailyProgressDao()

/**
 * Convenience function to get [MealSlotDao] from [AppDatabase].
 */
fun getMealSlotDao(appDatabase: AppDatabase): MealSlotDao = appDatabase.getMealSlotDao()

/**
 * Convenience function to get [WeightHistoryDao] from [AppDatabase].
 */
fun getWeightHistoryDao(appDatabase: AppDatabase): WeightHistoryDao = appDatabase.getWeightHistoryDao()
