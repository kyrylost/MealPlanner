package dev.stukalo.mealplanner.data.network.edamam.recipe.source

import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeResponseNetModel

interface EdamamRecipeNetSource {
    suspend fun getRecipesByMacros(
        type: String,
        calories: String,
        carbohydrates: String,
        fats: String,
        proteins: String,
        mealType: String,
    ): EdamamRecipeResponseNetModel

    suspend fun getRecipesByUrl(url: String): EdamamRecipeResponseNetModel
}
