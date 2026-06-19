package dev.stukalo.mealplanner.presentation.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.search.screen.SearchScreen

fun NavGraphBuilder.searchNavigationGraph(
    navController: NavHostController,
) {
    composable<NavigationDirection.Search> {
        SearchScreen()
    }
}
