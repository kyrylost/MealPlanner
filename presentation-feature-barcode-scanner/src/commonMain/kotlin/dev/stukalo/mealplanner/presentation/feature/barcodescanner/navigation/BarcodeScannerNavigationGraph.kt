package dev.stukalo.mealplanner.presentation.feature.barcodescanner.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.BarcodeScannerScreen

/**
 * Adds the Barcode Scanner screen to the navigation graph.
 *
 * @param navController The root navigation controller for cross-feature navigation.
 */
fun NavGraphBuilder.barcodeScannerNavigationGraph(navController: NavHostController) {
    composable<NavigationDirection.BarcodeScanner> {
        BarcodeScannerScreen(
            onBackClick = { navController.popBackStack() },
            onNavigateToProductDetails = { barcode ->
                navController.navigate(NavigationDirection.ProductDetails(barcode = barcode)) {
                    launchSingleTop = true
                }
            }
        )
    }
}
