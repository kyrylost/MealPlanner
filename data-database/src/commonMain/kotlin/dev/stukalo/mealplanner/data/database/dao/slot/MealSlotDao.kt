package dev.stukalo.mealplanner.data.database.dao.slot

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealSlotDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: MealSlotDatabaseModel)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(items: List<MealSlotDatabaseModel>)

    @Query("SELECT * FROM MealSlotDatabaseModel ORDER BY startTime ASC")
    fun getAllSlotsAsFlow(): Flow<List<MealSlotDatabaseModel>>

    @Query("SELECT * FROM MealSlotDatabaseModel WHERE id = :id")
    suspend fun getSlotById(id: Int): MealSlotDatabaseModel?

    @Query("UPDATE MealSlotDatabaseModel SET isConsumed = :isConsumed WHERE id = :id")
    suspend fun updateConsumedStatus(id: Int, isConsumed: Boolean)

    @Query("UPDATE MealSlotDatabaseModel SET isConsumed = 0")
    suspend fun resetAllConsumedStatus()
}