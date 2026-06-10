package dev.stukalo.mealplanner.data.network.edamam.impl.base.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest

internal fun edamamBaseClient(client: HttpClient) = client.config {
    install(DefaultRequest) {
        url {
            host = "api.edamam.com"
        }
    }
}