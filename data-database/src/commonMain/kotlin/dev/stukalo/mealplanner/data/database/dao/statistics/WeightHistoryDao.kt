package dev.stukalo.mealplanner.data.database.dao.statistics

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.stukalo.mealplanner.data.database.model.statistics.WeightHistoryDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface WeightHistoryDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: WeightHistoryDatabaseModel)

    @Query("SELECT * FROM WeightHistoryDatabaseModel ORDER BY date DESC")
    fun getAllAsFlow(): Flow<List<WeightHistoryDatabaseModel>>

    @Query("SELECT * FROM WeightHistoryDatabaseModel WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    fun getByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<WeightHistoryDatabaseModel>>
}
