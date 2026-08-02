package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.LogRecipeConsumedUseCase

internal class LogRecipeConsumedUseCaseImpl(private val logProductConsumedUseCase: LogProductConsumedUseCase) :
    LogRecipeConsumedUseCase {
    override suspend fun invoke(recipe: RecipeDomainModel, weight: Float?): Result<Unit> =
        logProductConsumedUseCase(recipe.product, weight)
}
