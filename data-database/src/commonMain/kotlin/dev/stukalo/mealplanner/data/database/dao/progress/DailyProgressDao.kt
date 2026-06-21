package dev.stukalo.mealplanner.data.database.dao.progress

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.stukalo.mealplanner.data.database.model.progress.DailyProgressDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface DailyProgressDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: DailyProgressDatabaseModel)

    @Query("SELECT * FROM DailyProgressDatabaseModel WHERE date = :date")
    suspend fun getProgressByDate(date: LocalDate): DailyProgressDatabaseModel?

    @Query("SELECT * FROM DailyProgressDatabaseModel WHERE date = :date")
    fun getProgressByDateAsFlow(date: LocalDate): Flow<DailyProgressDatabaseModel?>

    @Query("SELECT * FROM DailyProgressDatabaseModel ORDER BY date DESC")
    fun getAllProgressAsFlow(): Flow<List<DailyProgressDatabaseModel>>

    @Query("SELECT * FROM DailyProgressDatabaseModel WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getProgressByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<DailyProgressDatabaseModel>>
}
