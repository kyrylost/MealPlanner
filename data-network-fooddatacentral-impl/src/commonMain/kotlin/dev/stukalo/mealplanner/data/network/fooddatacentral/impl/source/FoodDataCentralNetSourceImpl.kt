package dev.stukalo.mealplanner.data.network.fooddatacentral.impl.source

import dev.stukalo.mealplanner.data.network.core.source.BaseNetSource
import dev.stukalo.mealplanner.data.network.fooddatacentral.source.FoodDataCentralNetSource
import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCSearchResponseNetModel
import dev.stukalo.mealplanner.data.network.fooddatacentral.impl.source.route.FoodDataCentralRoutes
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class FoodDataCentralNetSourceImpl(
    client: HttpClient,
) : BaseNetSource(client), FoodDataCentralNetSource {

    override suspend fun searchProduct(
        query: String,
        pageSize: Int,
        pageNumber: Int,
    ): FDCSearchResponseNetModel {
        return performRequest {
            get(FoodDataCentralRoutes.Search.route) {
                parameter("query", query)
                parameter("pageSize", pageSize)
                parameter("pageNumber", pageNumber)
            }
        }
    }
}
