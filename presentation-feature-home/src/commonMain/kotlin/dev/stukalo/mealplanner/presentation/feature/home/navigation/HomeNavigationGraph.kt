package dev.stukalo.mealplanner.presentation.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.ext.safeNavigation
import dev.stukalo.mealplanner.presentation.feature.home.screen.HomeScreen

fun NavGraphBuilder.homeNavigationGraph(
    appNavController: NavHostController,
) {
    composable<NavigationDirection.Home> {
        HomeScreen(
            onNavigateToRecipeDetails = { recipeId ->
                appNavController.safeNavigation(NavigationDirection.RecipeDetails(recipeId))
            }
        )
    }
}
