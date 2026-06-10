package dev.stukalo.mealplanner.presentation.feature.barcodescanner.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.BarcodeScannerScreen

fun NavGraphBuilder.barcodeScannerNavigationGraph(
    navController: NavHostController,
) {
    composable<NavigationDirection.BarcodeScanner> {
        BarcodeScannerScreen()
    }
}
