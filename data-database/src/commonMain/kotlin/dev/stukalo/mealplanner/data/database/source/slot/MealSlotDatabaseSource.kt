package dev.stukalo.mealplanner.data.database.source.slot

import dev.stukalo.mealplanner.data.database.dao.slot.MealSlotDao
import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import kotlinx.coroutines.flow.Flow

class MealSlotDatabaseSource(private val dao: MealSlotDao) {
    suspend fun insert(slot: MealSlotDatabaseModel): Result<Unit> = runCatching {
        dao.insert(slot)
    }

    suspend fun insertAll(slots: List<MealSlotDatabaseModel>): Result<Unit> = runCatching {
        dao.insertAll(slots)
    }

    fun getAllSlotsAsFlow(): Flow<List<MealSlotDatabaseModel>> = dao.getAllSlotsAsFlow()

    suspend fun getSlotById(id: Int): MealSlotDatabaseModel? = dao.getSlotById(id)

    suspend fun updateConsumedStatus(id: Int, isConsumed: Boolean): Result<Unit> = runCatching {
        dao.updateConsumedStatus(id, isConsumed)
    }

    suspend fun resetAllConsumedStatus(): Result<Unit> = runCatching {
        dao.resetAllConsumedStatus()
    }
}
