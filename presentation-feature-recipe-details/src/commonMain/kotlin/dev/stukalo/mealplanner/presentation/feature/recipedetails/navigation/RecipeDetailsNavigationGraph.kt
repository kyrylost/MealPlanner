package dev.stukalo.mealplanner.presentation.feature.recipedetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.RecipeDetailsScreen

fun NavGraphBuilder.recipeDetailsNavigationGraph(
    navController: NavHostController,
) {
    composable<NavigationDirection.RecipeDetails> { backStackEntry ->
        val recipeDetails: NavigationDirection.RecipeDetails = backStackEntry.toRoute()
        RecipeDetailsScreen(
            recipeId = recipeDetails.recipeId,
            onBackClick = { navController.popBackStack() }
        )
    }
}
