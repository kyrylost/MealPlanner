package dev.stukalo.mealplanner.data.network.edamam.recipe.source

import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeDetailsResponseNetModel
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeResponseNetModel

interface EdamamRecipeNetSource {
    suspend fun getRecipesByMacros(
        type: String,
        calories: String,
        carbohydrates: String,
        fats: String,
        proteins: String,
        mealTypes: List<String>,
        query: String? = null,
    ): EdamamRecipeResponseNetModel

    suspend fun getRecipesByUrl(url: String): EdamamRecipeResponseNetModel

    suspend fun getRecipeById(id: String): EdamamRecipeDetailsResponseNetModel
}
