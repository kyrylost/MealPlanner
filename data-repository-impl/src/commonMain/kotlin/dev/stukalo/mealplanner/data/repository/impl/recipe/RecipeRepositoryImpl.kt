package dev.stukalo.mealplanner.data.repository.impl.recipe

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.stukalo.mealplanner.data.network.edamam.recipe.source.EdamamRecipeNetSource
import dev.stukalo.mealplanner.data.repository.impl.recipe.mapper.RecipeMapper
import dev.stukalo.mealplanner.data.repository.impl.recipe.paging.RecipePagingSource
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

internal class RecipeRepositoryImpl(
    private val edamamRecipeNetSource: EdamamRecipeNetSource,
    private val recipeMapper: RecipeMapper
) : RecipeRepository {
    override fun getRecipesByNutrients(
        type: String,
        calories: IntRange,
        carbohydrates: IntRange,
        fats: IntRange,
        proteins: IntRange,
        mealTypes: List<MealTypeDomainModel>,
        query: String?
    ): Flow<PagingData<RecipeDomainModel>> = Pager(
        config =
        PagingConfig(
            pageSize = RECIPES_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            RecipePagingSource(
                edamamRecipeNetSource = edamamRecipeNetSource,
                recipeMapper = recipeMapper,
                type = type,
                calories = "${calories.first}-${calories.last}",
                carbohydrates = "${carbohydrates.first}-${carbohydrates.last}",
                fats = "${fats.first}-${fats.last}",
                proteins = "${proteins.first}-${proteins.last}",
                mealTypes = mealTypes.map { it.name.lowercase().replace("_", "") },
                query = query
            )
        }
    ).flow

    override suspend fun getRecipeById(id: String): Result<RecipeDomainModel> = try {
        val response = edamamRecipeNetSource.getRecipeById(id)
        val recipe = response.recipe?.let(recipeMapper::mapTo)
        if (recipe != null) {
            Result.success(recipe)
        } else {
            Result.failure(Exception("Recipe not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

private const val RECIPES_PAGE_SIZE = 20
