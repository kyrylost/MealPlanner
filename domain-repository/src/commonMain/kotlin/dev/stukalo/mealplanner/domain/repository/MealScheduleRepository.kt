package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalTime

interface MealScheduleRepository {
    fun getMealSlotsAsFlow(): Flow<List<MealSlotDomainModel>>

    suspend fun updateConsumedStatus(id: Int, isConsumed: Boolean): Result<Unit>

    suspend fun updateSlotTime(id: Int, startTime: LocalTime): Result<Unit>

    suspend fun resetDailyConsumedStatus(): Result<Unit>
}
