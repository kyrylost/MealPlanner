package dev.stukalo.mealplanner.domain.usecase.impl.recipes

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import kotlinx.coroutines.flow.Flow

internal class GetRecipesUseCaseImpl(
    private val recipeRepository: RecipeRepository,
): GetRecipesUseCase {
    override operator fun invoke(
        calories: IntRange,
        carbohydrates: IntRange,
        fats: IntRange,
        proteins: IntRange,
        mealType: MealTypeDomainModel,
    ): Flow<PagingData<RecipeDomainModel>> {
        return recipeRepository.getRecipesByNutrients(
            type = RECIPE_TYPE_ANY,
            calories = calories,
            carbohydrates = carbohydrates,
            fats = fats,
            proteins = proteins,
            mealType = mealType,
        )
    }
}

private const val RECIPE_TYPE_ANY = "any" //Available also: public, user, edamam-generic
