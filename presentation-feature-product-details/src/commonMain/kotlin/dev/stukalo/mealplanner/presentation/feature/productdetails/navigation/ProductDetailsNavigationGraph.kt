package dev.stukalo.mealplanner.presentation.feature.productdetails.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.ProductDetailsScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.productDetailsNavigationGraph(navController: NavHostController) {
    composable<NavigationDirection.ProductDetails> { backStackEntry ->
        val route: NavigationDirection.ProductDetails = backStackEntry.toRoute()
        ProductDetailsScreen(
            viewModel = koinViewModel { parametersOf(route.productId, route.barcode) },
            onBackClick = { navController.popBackStack() }
        )
    }
}
