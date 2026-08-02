package dev.stukalo.mealplanner.domain.usecase.recipes

import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel

interface LogRecipeConsumedUseCase {
    suspend operator fun invoke(recipe: RecipeDomainModel, weight: Float? = null): Result<Unit>
}
