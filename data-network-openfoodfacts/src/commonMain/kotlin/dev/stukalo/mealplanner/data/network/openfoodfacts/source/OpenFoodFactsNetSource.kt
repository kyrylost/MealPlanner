package dev.stukalo.mealplanner.data.network.openfoodfacts.source

import dev.stukalo.mealplanner.data.network.openfoodfacts.model.OFFProductResponseNetModel

interface OpenFoodFactsNetSource {
    suspend fun getProductByBarcode(barcode: String): OFFProductResponseNetModel
}
