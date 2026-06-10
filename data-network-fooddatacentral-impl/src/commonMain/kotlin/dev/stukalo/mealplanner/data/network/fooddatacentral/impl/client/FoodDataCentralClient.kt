package dev.stukalo.mealplanner.data.network.fooddatacentral.impl.client

import dev.stukalo.mealplanner.data.network.core.ApiKeys
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest

internal fun foodDataCentralClient(client: HttpClient) = client.config {
    install(DefaultRequest) {
        url {
            host = "api.nal.usda.gov"
            parameters.append("api_key", ApiKeys.USDA_FDC_API_KEY)
        }
    }
}
