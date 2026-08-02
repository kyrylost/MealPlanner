package dev.stukalo.mealplanner.presentation.feature.main.navigation.inner

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.transitions.NavigationTransitions
import dev.stukalo.mealplanner.presentation.feature.home.navigation.homeNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.product.search.navigation.productSearchNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.settings.navigation.settingsNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.statistics.navigation.statisticsNavigationGraph

@Composable
internal fun InnerMainNavigationGraph(
    mainNavController: NavHostController,
    appNavController: NavHostController,
    startDestination: NavigationDirection,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = mainNavController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = {
            val initialTab = MainTab.entries.find { tab -> initialState.destination.hasRoute(tab.route::class) }
            val targetTab = MainTab.entries.find { tab -> targetState.destination.hasRoute(tab.route::class) }

            if (initialTab != null && targetTab != null) {
                NavigationTransitions.tabEnter(isRight = targetTab.ordinal > initialTab.ordinal)
            } else {
                EnterTransition.None
            }
        },
        exitTransition = {
            val initialTab = MainTab.entries.find { tab -> initialState.destination.hasRoute(tab.route::class) }
            val targetTab = MainTab.entries.find { tab -> targetState.destination.hasRoute(tab.route::class) }

            if (initialTab != null && targetTab != null) {
                NavigationTransitions.tabExit(isRight = targetTab.ordinal > initialTab.ordinal)
            } else {
                ExitTransition.None
            }
        },
        popEnterTransition = {
            val initialTab = MainTab.entries.find { tab -> initialState.destination.hasRoute(tab.route::class) }
            val targetTab = MainTab.entries.find { tab -> targetState.destination.hasRoute(tab.route::class) }

            if (initialTab != null && targetTab != null) {
                NavigationTransitions.tabEnter(isRight = targetTab.ordinal > initialTab.ordinal)
            } else {
                EnterTransition.None
            }
        },
        popExitTransition = {
            val initialTab = MainTab.entries.find { tab -> initialState.destination.hasRoute(tab.route::class) }
            val targetTab = MainTab.entries.find { tab -> targetState.destination.hasRoute(tab.route::class) }

            if (initialTab != null && targetTab != null) {
                NavigationTransitions.tabExit(isRight = targetTab.ordinal > initialTab.ordinal)
            } else {
                ExitTransition.None
            }
        }
    ) {
        homeNavigationGraph(
            appNavController = appNavController
        )

        productSearchNavigationGraph(
            navController = mainNavController,
            appNavController = appNavController
        )

        statisticsNavigationGraph()

        settingsNavigationGraph()
    }
}
