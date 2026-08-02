package dev.stukalo.mealplanner.presentation.feature.product.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.ProductSearchScreen

fun NavGraphBuilder.productSearchNavigationGraph(navController: NavController, appNavController: NavController) {
    composable<NavigationDirection.ProductSearch> {
        ProductSearchScreen(
            onBackClick = { navController.popBackStack() },
            onNavigateToBarcodeScanner = {
                appNavController.navigate(NavigationDirection.BarcodeScanner)
            }
        )
    }
}
