package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase

class UpdateDailyProgressUseCaseImpl(private val nutritionRepository: NutritionRepository) :
    UpdateDailyProgressUseCase {
    override suspend fun invoke(progress: DailyProgressDomainModel): Result<Unit> =
        nutritionRepository.saveDailyProgress(progress)
}
