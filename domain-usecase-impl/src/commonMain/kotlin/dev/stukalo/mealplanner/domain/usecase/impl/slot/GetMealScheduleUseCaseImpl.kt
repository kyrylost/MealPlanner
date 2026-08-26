package dev.stukalo.mealplanner.domain.usecase.impl.slot

import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import dev.stukalo.mealplanner.domain.repository.MealSlotRepository
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import kotlinx.coroutines.flow.Flow

class GetMealScheduleUseCaseImpl(private val mealSlotRepository: MealSlotRepository) : GetMealScheduleUseCase {
    override fun invoke(): Flow<List<MealSlotDomainModel>> = mealSlotRepository.getMealSlotsAsFlow()
}
