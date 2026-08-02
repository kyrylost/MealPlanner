package dev.stukalo.mealplanner.data.network.edamam.impl.food.source.route

sealed class EdamamFoodRoutes(val route: String) {
    data object AutoComplete : EdamamFoodRoutes("auto-complete")
}
