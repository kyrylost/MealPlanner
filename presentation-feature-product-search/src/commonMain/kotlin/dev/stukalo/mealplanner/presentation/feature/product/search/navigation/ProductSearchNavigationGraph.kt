package dev.stukalo.mealplanner.presentation.feature.product.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.ProductSearchScreen

fun NavGraphBuilder.productSearchNavigationGraph() {
    composable<NavigationDirection.ProductSearch> {
        ProductSearchScreen()
    }
}
