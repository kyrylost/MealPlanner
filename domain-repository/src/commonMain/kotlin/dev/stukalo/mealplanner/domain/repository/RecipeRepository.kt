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
        mealTypes: List<MealTypeDomainModel>,
        query: String? = null,
    ): Flow<PagingData<RecipeDomainModel>>

    suspend fun getRecipeById(id: String): Result<RecipeDomainModel>
}
