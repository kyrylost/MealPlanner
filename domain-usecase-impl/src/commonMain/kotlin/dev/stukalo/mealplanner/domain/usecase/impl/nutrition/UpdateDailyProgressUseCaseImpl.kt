package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase

/**
 * Use case for updating the daily progress and syncing it with health services.
 */
class UpdateDailyProgressUseCaseImpl(
    private val nutritionRepository: NutritionRepository,
    private val healthRepository: HealthRepository
) : UpdateDailyProgressUseCase {
    override suspend fun invoke(progress: DailyProgressDomainModel): Result<Unit> =
        nutritionRepository.saveDailyProgress(progress).onSuccess {
            healthRepository.writeNutrition(progress.date, progress)
        }
}
