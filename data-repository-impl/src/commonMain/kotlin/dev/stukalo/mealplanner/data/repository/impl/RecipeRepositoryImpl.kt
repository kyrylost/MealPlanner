package dev.stukalo.mealplanner.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.stukalo.mealplanner.data.network.edamam.recipe.source.EdamamRecipeNetSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.RecipeMapper
import dev.stukalo.mealplanner.data.repository.impl.paging.RecipePagingSource
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

internal class RecipeRepositoryImpl(
    private val edamamRecipeNetSource: EdamamRecipeNetSource,
    private val recipeMapper: RecipeMapper,
): RecipeRepository {

    override fun getRecipesByNutrients(
        type: String,
        calories: IntRange,
        carbohydrates: IntRange,
        fats: IntRange,
        proteins: IntRange,
        mealType: MealTypeDomainModel,
    ): Flow<PagingData<RecipeDomainModel>> {
        return Pager(
            config = PagingConfig(
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
                    mealType = mealType.name.lowercase(),
                )
            }
        ).flow
    }
}

private const val RECIPES_PAGE_SIZE = 20
