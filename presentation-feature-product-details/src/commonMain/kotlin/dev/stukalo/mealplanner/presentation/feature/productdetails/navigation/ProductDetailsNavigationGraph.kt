package dev.stukalo.mealplanner.presentation.feature.productdetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.ProductDetailsScreen

fun NavGraphBuilder.productDetailsNavigationGraph(navController: NavHostController) {
    composable<NavigationDirection.ProductDetails> { backStackEntry ->
        val route: NavigationDirection.ProductDetails = backStackEntry.toRoute()
        ProductDetailsScreen(
            productId = route.productId,
            barcode = route.barcode,
            onBackClick = { navController.popBackStack() }
        )
    }
}
