package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetDailyProgressUseCaseImpl(private val nutritionRepository: NutritionRepository) : GetDailyProgressUseCase {
    override fun invoke(date: LocalDate): Flow<DailyProgressDomainModel?> =
        nutritionRepository.getDailyProgressAsFlow(date)
}
