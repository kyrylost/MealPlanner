package dev.stukalo.mealplanner.presentation.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.main.screen.MainFlowScreen

fun NavGraphBuilder.mainFlowRoute(navController: NavHostController) {
    composable<NavigationDirection.Home> {
        MainFlowScreen(
            appNavController = navController,
            initialTab = NavigationDirection.Home
        )
    }

    composable<NavigationDirection.Statistics> {
        MainFlowScreen(
            appNavController = navController,
            initialTab = NavigationDirection.Statistics
        )
    }

    composable<NavigationDirection.ProductSearch> {
        MainFlowScreen(
            appNavController = navController,
            initialTab = NavigationDirection.ProductSearch
        )
    }

    composable<NavigationDirection.Settings> {
        MainFlowScreen(
            appNavController = navController,
            initialTab = NavigationDirection.Settings
        )
    }
}
