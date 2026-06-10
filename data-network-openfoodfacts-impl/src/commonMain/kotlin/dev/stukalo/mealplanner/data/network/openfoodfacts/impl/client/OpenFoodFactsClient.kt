package dev.stukalo.mealplanner.data.network.openfoodfacts.impl.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.http.path

fun openFoodFactsClient(client: HttpClient) = client.config {
    install(DefaultRequest) {
        url {
            host = "world.openfoodfacts.org"
            path("api/v2/")
        }
    }
}
