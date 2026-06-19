package dev.stukalo.mealplanner.presentation.feature.main.navigation.inner

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.navigation.barcodeScannerNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.home.navigation.homeNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.main.navigation.ext.navigateTab
import dev.stukalo.mealplanner.presentation.feature.search.navigation.searchNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.statistics.navigation.statisticsNavigationGraph

@Composable
internal fun InnerMainNavigationGraph(
    mainNavController: NavHostController,
    appNavController: NavHostController,
    startDestination: NavigationDirection,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = mainNavController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        homeNavigationGraph(
            appNavController = appNavController,
            onNavigateToBarcodeScanner = {
                mainNavController.navigateTab(NavigationDirection.BarcodeScanner)
            },
            onNavigateToSearch = {
                mainNavController.navigateTab(NavigationDirection.Search)
            }
        )

        searchNavigationGraph(
            navController = appNavController
        )

        statisticsNavigationGraph(
            navController = appNavController
        )

        barcodeScannerNavigationGraph(
            navController = appNavController
        )
    }
}