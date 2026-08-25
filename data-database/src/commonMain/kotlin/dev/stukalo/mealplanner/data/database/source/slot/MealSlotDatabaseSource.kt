package dev.stukalo.mealplanner.data.database.source.slot

import dev.stukalo.mealplanner.data.database.dao.slot.MealSlotDao
import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class MealSlotDatabaseSource(private val dao: MealSlotDao) {
    suspend fun insert(slot: MealSlotDatabaseModel): Result<Unit> = runCatching {
        dao.insert(slot)
    }

    suspend fun insertAll(slots: List<MealSlotDatabaseModel>): Result<Unit> = runCatching {
        dao.insertAll(slots)
    }

    fun getAllSlotsAsFlow(): Flow<List<MealSlotDatabaseModel>> = dao.getAllSlotsAsFlow()

    suspend fun getSlotById(id: Int): MealSlotDatabaseModel? = dao.getSlotById(id)

    suspend fun updateLastConsumedDate(id: Int, lastConsumedDate: LocalDate?): Result<Unit> = runCatching {
        dao.updateLastConsumedDate(id, lastConsumedDate)
    }

    suspend fun updateSlotTime(id: Int, startTime: LocalTime): Result<Unit> = runCatching {
        dao.updateSlotTime(id, startTime)
    }

    suspend fun resetAllConsumedStatus(): Result<Unit> = runCatching {
        dao.resetAllConsumedStatus()
    }
}
