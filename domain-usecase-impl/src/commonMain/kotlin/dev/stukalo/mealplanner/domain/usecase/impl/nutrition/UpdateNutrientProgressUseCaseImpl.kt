package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_CARB_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_FAT_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_PROTEIN_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateNutrientProgressUseCase
import kotlinx.coroutines.flow.first
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

class UpdateNutrientProgressUseCaseImpl(
    private val nutritionRepository: NutritionRepository,
    private val clock: Clock
) : UpdateNutrientProgressUseCase {
    override suspend fun invoke(type: NutrientTypeDomainModel, amount: Float): Result<Unit> {
        val today = clock.todayIn(TimeZone.currentSystemDefault())

        val currentProgress =
            nutritionRepository.getDailyProgressAsFlow(today).first()
                ?: DailyProgressDomainModel(
                    date = today,
                    consumedCalories = 0.0,
                    consumedProteins = 0.0,
                    consumedFats = 0.0,
                    consumedCarbohydrates = 0.0
                )

        val newProgress =
            when (type) {
                NutrientTypeDomainModel.PROTEIN ->
                    currentProgress.copy(
                        consumedProteins = currentProgress.consumedProteins + amount,
                        consumedCalories =
                        currentProgress.consumedCalories +
                            (amount * CALORIES_PER_PROTEIN_GRAM).toDouble()
                    )
                NutrientTypeDomainModel.FATS ->
                    currentProgress.copy(
                        consumedFats = currentProgress.consumedFats + amount,
                        consumedCalories =
                        currentProgress.consumedCalories +
                            (amount * CALORIES_PER_FAT_GRAM).toDouble()
                    )
                NutrientTypeDomainModel.CARBOHYDRATES ->
                    currentProgress.copy(
                        consumedCarbohydrates = currentProgress.consumedCarbohydrates + amount,
                        consumedCalories =
                        currentProgress.consumedCalories +
                            (amount * CALORIES_PER_CARB_GRAM).toDouble()
                    )
            }
        return nutritionRepository.saveDailyProgress(newProgress)
    }
}
