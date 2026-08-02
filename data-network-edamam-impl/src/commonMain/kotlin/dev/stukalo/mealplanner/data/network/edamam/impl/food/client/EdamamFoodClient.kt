package dev.stukalo.mealplanner.data.network.edamam.impl.food.client

import dev.stukalo.mealplanner.data.network.core.ApiKeys
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest

internal fun edamamFoodClient(client: HttpClient) = client.config {
    install(DefaultRequest) {
        url {
            parameters.apply {
                append(
                    name = "app_id",
                    value = ApiKeys.EDAMAM_FOOD_API_APP_ID
                )
                append(
                    name = "app_key",
                    value = ApiKeys.EDAMAM_FOOD_API_APP_KEY
                )
            }
        }
    }
}
