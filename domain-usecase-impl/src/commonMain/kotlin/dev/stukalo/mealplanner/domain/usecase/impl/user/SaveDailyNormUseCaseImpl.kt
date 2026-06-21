package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.user.SaveDailyNormUseCase

internal class SaveDailyNormUseCaseImpl(
    private val nutritionRepository: NutritionRepository
) : SaveDailyNormUseCase {
    override suspend fun invoke(dailyNorm: DailyNormDomainModel): Result<Unit> {
        return nutritionRepository.saveDailyNorm(dailyNorm)
    }
}
