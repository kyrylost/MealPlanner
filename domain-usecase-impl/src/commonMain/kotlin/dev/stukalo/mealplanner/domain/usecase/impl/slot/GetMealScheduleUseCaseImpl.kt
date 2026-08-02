package dev.stukalo.mealplanner.domain.usecase.impl.slot

import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import dev.stukalo.mealplanner.domain.repository.MealScheduleRepository
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import kotlinx.coroutines.flow.Flow

class GetMealScheduleUseCaseImpl(private val mealScheduleRepository: MealScheduleRepository) :
    GetMealScheduleUseCase {
    override fun invoke(): Flow<List<MealSlotDomainModel>> = mealScheduleRepository.getMealSlotsAsFlow()
}
