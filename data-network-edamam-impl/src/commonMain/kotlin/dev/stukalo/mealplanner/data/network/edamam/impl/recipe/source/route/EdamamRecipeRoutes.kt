package dev.stukalo.mealplanner.data.network.edamam.impl.recipe.source.route


sealed class EdamamRecipeRoutes(val route: String) {

    data object Recipes: EdamamRecipeRoutes("api/recipes/v2")

}