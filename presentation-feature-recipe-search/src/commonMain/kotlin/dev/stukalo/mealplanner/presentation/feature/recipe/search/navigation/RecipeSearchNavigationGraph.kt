package dev.stukalo.mealplanner.presentation.feature.recipe.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.RecipeSearchScreen

fun NavGraphBuilder.recipeSearchNavigationGraph(
    navController: NavHostController,
) {
    composable<NavigationDirection.RecipeSearch> {
        RecipeSearchScreen(navController = navController)
    }
}
