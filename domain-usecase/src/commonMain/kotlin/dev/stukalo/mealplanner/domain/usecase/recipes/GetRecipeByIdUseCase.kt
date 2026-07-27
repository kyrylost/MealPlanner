package dev.stukalo.mealplanner.domain.usecase.recipes

import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel

interface GetRecipeByIdUseCase {
    suspend operator fun invoke(id: String): Result<RecipeDomainModel>
}
