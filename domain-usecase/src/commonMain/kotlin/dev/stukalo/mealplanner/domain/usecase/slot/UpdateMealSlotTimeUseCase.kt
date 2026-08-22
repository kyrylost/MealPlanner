package dev.stukalo.mealplanner.domain.usecase.slot

import kotlinx.datetime.LocalTime

/**
 * Use case for updating the start time of a meal slot.
 */
interface UpdateMealSlotTimeUseCase {
    /**
     * Updates the start time of a meal slot.
     *
     * @param slotId The ID of the slot to update.
     * @param startTime The new start time for the slot.
     * @return Result of the operation.
     */
    suspend operator fun invoke(slotId: Int, startTime: LocalTime): Result<Unit>
}
