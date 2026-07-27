package dev.stukalo.mealplanner.presentation.feature.main.navigation.inner

import androidx.compose.ui.graphics.vector.ImageVector
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.main.icons.BarcodeScanner
import dev.stukalo.mealplanner.presentation.feature.main.icons.Home
import dev.stukalo.mealplanner.presentation.feature.main.icons.NavigationBarIcons
import dev.stukalo.mealplanner.presentation.feature.main.icons.Search
import dev.stukalo.mealplanner.presentation.feature.main.icons.Statistics

enum class MainTab(
    val icon: ImageVector,
    val route: NavigationDirection,
) {
    Home(
        icon = NavigationBarIcons.Home,
        route = NavigationDirection.Home
    ),
    Statistics(
        icon = NavigationBarIcons.Statistics,
        route = NavigationDirection.Statistics
    ),
    Search(
        icon = NavigationBarIcons.Search,
        route = NavigationDirection.ProductSearch
    ),
    BarcodeScanner(
        icon = NavigationBarIcons.BarcodeScanner,
        route = NavigationDirection.BarcodeScanner
    ),
}