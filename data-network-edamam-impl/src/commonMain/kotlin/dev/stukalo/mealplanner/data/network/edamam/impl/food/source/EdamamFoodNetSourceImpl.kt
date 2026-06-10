package dev.stukalo.mealplanner.data.network.edamam.impl.food.source

import dev.stukalo.mealplanner.data.network.core.source.BaseNetSource
import dev.stukalo.mealplanner.data.network.edamam.food.source.EdamamFoodNetSource
import dev.stukalo.mealplanner.data.network.edamam.impl.food.source.route.EdamamFoodRoutes
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class EdamamFoodNetSourceImpl(
    client: HttpClient,
): BaseNetSource(client), EdamamFoodNetSource {

    override suspend fun getAutoCompleteHints(
        query: String,
        limit: String,
    ): List<String> {
        return performRequest {
            get(EdamamFoodRoutes.AutoComplete.route) {
                parameter("q", query)
                parameter("limit", limit)
            }
        }
    }
}
