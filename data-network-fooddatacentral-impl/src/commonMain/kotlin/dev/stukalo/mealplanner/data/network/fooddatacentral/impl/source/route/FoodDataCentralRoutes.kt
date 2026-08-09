package dev.stukalo.mealplanner.data.network.fooddatacentral.impl.source.route

internal enum class FoodDataCentralRoutes(val route: String) {
    Search("/fdc/v1/foods/search"),
    Details("/fdc/v1/food/")
}
