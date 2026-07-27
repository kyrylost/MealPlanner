package dev.stukalo.mealplanner.domain.usecase.slot

import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import kotlinx.coroutines.flow.Flow

interface GetMealScheduleUseCase {
    operator fun invoke(): Flow<List<MealSlotDomainModel>>
}
