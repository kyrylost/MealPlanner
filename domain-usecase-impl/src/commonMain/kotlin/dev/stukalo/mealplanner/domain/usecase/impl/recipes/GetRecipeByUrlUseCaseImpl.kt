package dev.stukalo.mealplanner.domain.usecase.impl.recipes

import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipeByIdUseCase

class GetRecipeByIdUseCaseImpl(
    private val recipeRepository: RecipeRepository
) : GetRecipeByIdUseCase {
    override suspend fun invoke(id: String): Result<RecipeDomainModel> {
        return recipeRepository.getRecipeById(id)
    }
}