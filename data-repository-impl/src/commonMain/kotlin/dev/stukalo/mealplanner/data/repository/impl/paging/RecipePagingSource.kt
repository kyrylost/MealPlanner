package dev.stukalo.mealplanner.data.repository.impl.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.stukalo.mealplanner.data.network.edamam.recipe.source.EdamamRecipeNetSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.RecipeMapper
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel

internal class RecipePagingSource(
    private val edamamRecipeNetSource: EdamamRecipeNetSource,
    private val recipeMapper: RecipeMapper,
    private val type: String,
    private val calories: String,
    private val carbohydrates: String,
    private val fats: String,
    private val proteins: String,
    private val mealTypes: List<String>,
    private val query: String?
) : PagingSource<String, RecipeDomainModel>() {
    override fun getRefreshKey(state: PagingState<String, RecipeDomainModel>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RecipeDomainModel> = try {
        val response =
            if (params.key == null) {
                edamamRecipeNetSource.getRecipesByMacros(
                    type = type,
                    calories = calories,
                    carbohydrates = carbohydrates,
                    fats = fats,
                    proteins = proteins,
                    mealTypes = mealTypes,
                    query = query
                )
            } else {
                edamamRecipeNetSource.getRecipesByUrl(params.key!!)
            }

        val recipes =
            response.hits
                ?.mapNotNull { hit ->
                    hit.recipe?.let(recipeMapper::mapTo)
                }.orEmpty()

        LoadResult.Page(
            data = recipes,
            prevKey = null,
            nextKey = response.links?.next?.href
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}
