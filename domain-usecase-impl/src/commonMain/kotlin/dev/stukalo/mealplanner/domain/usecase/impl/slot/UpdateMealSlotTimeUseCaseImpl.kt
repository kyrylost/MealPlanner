package dev.stukalo.mealplanner.domain.usecase.impl.slot

import dev.stukalo.mealplanner.domain.model.exception.MealSlotException
import dev.stukalo.mealplanner.domain.repository.MealSlotRepository
import dev.stukalo.mealplanner.domain.usecase.slot.SyncMealRemindersUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.UpdateMealSlotTimeUseCase
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalTime

/**
 * Implementation of [UpdateMealSlotTimeUseCase].
 * Validates that meal slot times maintain chronological order (Breakfast < Lunch < Dinner).
 */
internal class UpdateMealSlotTimeUseCaseImpl(
    private val repository: MealSlotRepository,
    private val syncMealRemindersUseCase: SyncMealRemindersUseCase
) : UpdateMealSlotTimeUseCase {
    override suspend fun invoke(slotId: Int, startTime: LocalTime): Result<Unit> {
        val slots = repository.getMealSlotsAsFlow().first().sortedBy { it.startTime }
        val slotToUpdate = slots.find { it.id == slotId } ?: return Result.failure(IllegalArgumentException())

        val index = slots.indexOf(slotToUpdate)

        // Validate against previous slot
        if (index > 0) {
            val prevSlot = slots[index - 1]
            if (startTime <= prevSlot.startTime) {
                return Result.failure(MealSlotException.MealOrderViolation())
            }
        }

        // Validate against next slot
        if (index < slots.size - 1) {
            val nextSlot = slots[index + 1]
            if (startTime >= nextSlot.startTime) {
                return Result.failure(MealSlotException.MealOrderViolation())
            }
        }

        return repository.updateSlotTime(slotId, startTime).onSuccess {
            syncMealRemindersUseCase()
        }
    }
}
