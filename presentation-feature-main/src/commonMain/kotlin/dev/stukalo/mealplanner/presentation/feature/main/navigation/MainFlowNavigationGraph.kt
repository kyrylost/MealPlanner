package dev.stukalo.mealplanner.presentation.feature.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.main.screen.MainFlowScreen
import dev.stukalo.mealplanner.presentation.feature.search.screen.SearchScreen

fun NavGraphBuilder.mainFlowRoute(
    navController: NavHostController,
) {
    composable<NavigationDirection.MainFlow> {
        MainFlowScreen(
            onNavigateToBarcodeScanner = {
                navController.navigate(NavigationDirection.BarcodeScanner)
            },
            onNavigateToSearch = {
                navController.navigate(NavigationDirection.Search)
            },
        )
    }

    composable<NavigationDirection.Search> {
        SearchScreen()
    }
}
