package dev.stukalo.mealplanner.data.network.openfoodfacts.impl.source

import dev.stukalo.mealplanner.data.network.core.source.BaseNetSource
import dev.stukalo.mealplanner.data.network.openfoodfacts.impl.source.route.OpenFoodFactsRoutes
import dev.stukalo.mealplanner.data.network.openfoodfacts.model.OFFProductResponseNetModel
import dev.stukalo.mealplanner.data.network.openfoodfacts.source.OpenFoodFactsNetSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class OpenFoodFactsNetSourceImpl(
    client: HttpClient,
): BaseNetSource(client), OpenFoodFactsNetSource {

    override suspend fun getProductByBarcode(barcode: String): OFFProductResponseNetModel {
        return performRequest {
            get(OpenFoodFactsRoutes.ProductByBarcode(barcode).route) {
                parameter("fields", "product_name,nutriments,image_url")
            }
        }
    }
}
