package dev.stukalo.mealplanner.domain.repository

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository for fetching recipe data.
 */
interface RecipeRepository {
    /**
     * Searches for recipes based on nutrient ranges and filters.
     *
     * @param type The search type (e.g., "public").
     * @param calories The range of calories per serving.
     * @param carbohydrates The range of carbohydrates in grams.
     * @param fats The range of fats in grams.
     * @param proteins The range of proteins in grams.
     * @param mealTypes The list of meal types to filter by.
     * @param query An optional search query.
     * @return A flow of paging data containing the matching recipes.
     */
    fun getRecipesByNutrients(
        type: String,
        calories: IntRange,
        carbohydrates: IntRange,
        fats: IntRange,
        proteins: IntRange,
        mealTypes: List<MealTypeDomainModel>,
        query: String? = null
    ): Flow<PagingData<RecipeDomainModel>>

    /**
     * Fetches a single recipe by its unique identifier.
     *
     * @param id The ID of the recipe to fetch.
     * @return A result containing the recipe or an error.
     */
    suspend fun getRecipeById(id: String): Result<RecipeDomainModel>
}
