package dev.stukalo.mealplanner.data.network.edamam.impl.recipe.source

import dev.stukalo.mealplanner.data.network.core.source.BaseNetSource
import dev.stukalo.mealplanner.data.network.edamam.impl.recipe.source.route.EdamamRecipeRoutes
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeResponseNetModel
import dev.stukalo.mealplanner.data.network.edamam.recipe.source.EdamamRecipeNetSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class EdamamRecipeNetSourceImpl(
    client: HttpClient,
): BaseNetSource(client), EdamamRecipeNetSource {

    override suspend fun getRecipesByMacros(
        type: String,
        calories: String,
        carbohydrates: String,
        fats: String,
        proteins: String,
        mealType: String
    ): EdamamRecipeResponseNetModel {
        return performRequest {
            get(EdamamRecipeRoutes.Recipes.route) {
                parameter("type", type)
                parameter("nutrients[ENERC_KCAL]", calories)
                parameter("nutrients[CHOCDF]", carbohydrates)
                parameter("nutrients[FAT]", fats)
                parameter("nutrients[PROCNT]", proteins)
                parameter("mealType", mealType)
            }
        }
    }

    override suspend fun getRecipesByUrl(url: String): EdamamRecipeResponseNetModel {
        return performRequest {
            get(url)
        }
    }
}
