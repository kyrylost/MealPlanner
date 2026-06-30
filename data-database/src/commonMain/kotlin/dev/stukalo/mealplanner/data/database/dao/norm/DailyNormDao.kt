package dev.stukalo.mealplanner.data.database.dao.norm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.stukalo.mealplanner.data.database.model.norm.DailyNormDatabaseModel

@Dao
interface DailyNormDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: DailyNormDatabaseModel)

    @Query("SELECT * FROM DailyNormDatabaseModel LIMIT 1")
    suspend fun getDailyNorm(): DailyNormDatabaseModel?

    @Query("SELECT * FROM DailyNormDatabaseModel LIMIT 1")
    fun getDailyNormAsFlow(): kotlinx.coroutines.flow.Flow<DailyNormDatabaseModel?>
}
