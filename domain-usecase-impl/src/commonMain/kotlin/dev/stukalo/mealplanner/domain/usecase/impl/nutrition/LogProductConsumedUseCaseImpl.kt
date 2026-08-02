package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

internal class LogProductConsumedUseCaseImpl(
    private val getDailyProgressUseCase: GetDailyProgressUseCase,
    private val updateDailyProgressUseCase: UpdateDailyProgressUseCase,
    private val clock: Clock,
) : LogProductConsumedUseCase {

    override suspend fun invoke(
        product: ProductDomainModel,
        weight: Float?,
    ): Result<Unit> = runCatching {
        val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val today = now.date

        val currentProgress = getDailyProgressUseCase(today).firstOrNull() ?: DailyProgressDomainModel(
            date = today,
            consumedCalories = 0.0,
            consumedProteins = 0.0,
            consumedFats = 0.0,
            consumedCarbohydrates = 0.0
        )

        val consumedCalories: Double
        val consumedProteins: Double
        val consumedFats: Double
        val consumedCarbohydrates: Double

        if (weight != null) {
            val ratio = (weight / 100.0)
            consumedCalories = (product.calories ?: 0f).toDouble() * ratio
            consumedProteins = (product.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }?.amount ?: 0f).toDouble() * ratio
            consumedFats = (product.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.FATS }?.amount ?: 0f).toDouble() * ratio
            consumedCarbohydrates = (product.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.CARBOHYDRATES }?.amount ?: 0f).toDouble() * ratio
        } else {
            consumedCalories = (product.caloriesTotal ?: product.calories ?: 0f).toDouble()
            val nutrients = product.nutrientsTotal ?: product.nutrients
            consumedProteins = (nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }?.amount ?: 0f).toDouble()
            consumedFats = (nutrients?.find { it.nutrientType == NutrientTypeDomainModel.FATS }?.amount ?: 0f).toDouble()
            consumedCarbohydrates = (nutrients?.find { it.nutrientType == NutrientTypeDomainModel.CARBOHYDRATES }?.amount ?: 0f).toDouble()
        }

        val updatedProgress = currentProgress.copy(
            consumedCalories = currentProgress.consumedCalories + consumedCalories,
            consumedProteins = currentProgress.consumedProteins + consumedProteins,
            consumedFats = currentProgress.consumedFats + consumedFats,
            consumedCarbohydrates = currentProgress.consumedCarbohydrates + consumedCarbohydrates
        )

        updateDailyProgressUseCase(updatedProgress).getOrThrow()
    }
}
