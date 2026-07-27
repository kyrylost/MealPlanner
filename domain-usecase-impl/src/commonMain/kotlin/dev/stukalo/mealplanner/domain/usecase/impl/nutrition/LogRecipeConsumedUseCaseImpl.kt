package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.LogRecipeConsumedUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class LogRecipeConsumedUseCaseImpl(
    private val getDailyProgressUseCase: GetDailyProgressUseCase,
    private val updateDailyProgressUseCase: UpdateDailyProgressUseCase,
    private val clock: Clock,
) : LogRecipeConsumedUseCase {

    override suspend fun invoke(
        recipe: RecipeDomainModel,
        weight: Float?,
    ): Result<Unit> {
        val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val today = now.date

        // 1. Update Daily Progress
        val currentProgress = getDailyProgressUseCase(today).firstOrNull() ?: DailyProgressDomainModel(
            date = today,
            consumedCalories = 0.0,
            consumedProteins = 0.0,
            consumedFats = 0.0,
            consumedCarbohydrates = 0.0
        )

        val recipeCalories: Double
        val recipeProteins: Double
        val recipeFats: Double
        val recipeCarbs: Double

        if (weight != null) {
            val ratio = weight / 100.0
            recipeCalories = (recipe.product.calories ?: 0f).toDouble() * ratio
            recipeProteins = (recipe.product.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }?.amount ?: 0f).toDouble() * ratio
            recipeFats = (recipe.product.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.FATS }?.amount ?: 0f).toDouble() * ratio
            recipeCarbs = (recipe.product.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.CARBOHYDRATES }?.amount ?: 0f).toDouble() * ratio
        } else {
            val nutrients = recipe.product.nutrientsTotal ?: recipe.product.nutrients
            recipeProteins = nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }?.amount?.toDouble() ?: 0.0
            recipeFats = nutrients?.find { it.nutrientType == NutrientTypeDomainModel.FATS }?.amount?.toDouble() ?: 0.0
            recipeCarbs = nutrients?.find { it.nutrientType == NutrientTypeDomainModel.CARBOHYDRATES }?.amount?.toDouble() ?: 0.0
            recipeCalories = recipe.product.caloriesTotal?.toDouble() ?: recipe.product.calories?.toDouble() ?: 0.0
        }

        val updatedProgress = currentProgress.copy(
            consumedCalories = currentProgress.consumedCalories + recipeCalories,
            consumedProteins = currentProgress.consumedProteins + recipeProteins,
            consumedFats = currentProgress.consumedFats + recipeFats,
            consumedCarbohydrates = currentProgress.consumedCarbohydrates + recipeCarbs
        )

        updateDailyProgressUseCase(updatedProgress)

        return Result.success(Unit)
    }
}