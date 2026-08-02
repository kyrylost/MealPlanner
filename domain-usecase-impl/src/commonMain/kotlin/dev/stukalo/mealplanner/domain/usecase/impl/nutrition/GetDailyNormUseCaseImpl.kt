package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import kotlinx.coroutines.flow.Flow

class GetDailyNormUseCaseImpl(private val nutritionRepository: NutritionRepository) : GetDailyNormUseCase {
    override fun invoke(): Flow<DailyNormDomainModel?> = nutritionRepository.getDailyNormAsFlow()
}
