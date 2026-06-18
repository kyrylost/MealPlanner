package dev.stukalo.mealplanner.domain.repository

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipesByNutrients(
        type: String,
        calories: IntRange,
        carbohydrates: IntRange,
        fats: IntRange,
        proteins: IntRange,
        mealType: MealTypeDomainModel,
    ): Flow<PagingData<RecipeDomainModel>>
}
