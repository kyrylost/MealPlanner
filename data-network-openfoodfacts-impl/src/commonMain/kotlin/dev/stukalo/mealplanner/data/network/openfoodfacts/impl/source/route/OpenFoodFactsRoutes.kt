package dev.stukalo.mealplanner.data.network.openfoodfacts.impl.source.route


sealed class OpenFoodFactsRoutes(val route: String) {

    data class ProductByBarcode(
        private val barcode: String,
    ): OpenFoodFactsRoutes("product/$barcode/")
}
