package dev.stukalo.mealplanner.presentation.feature.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.transitions.NavigationTransitions
import dev.stukalo.mealplanner.presentation.feature.filters.navigation.filtersNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.gateway.navigation.gatewayNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.main.navigation.inner.MainTab
import dev.stukalo.mealplanner.presentation.feature.main.navigation.mainFlowRoute
import dev.stukalo.mealplanner.presentation.feature.onboarding.navigation.onboardingNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.recipe.search.navigation.recipeSearchNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.recipedetails.navigation.recipeDetailsNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.welcome.navigation.welcomeNavigationGraph

@Composable
internal fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDirection.Gateway,
        enterTransition = {
            if (isDrillDown(targetState)) {
                NavigationTransitions.drillDownEnter
            } else {
                EnterTransition.None
            }
        },
        exitTransition = {
            if (isDrillDown(targetState)) {
                NavigationTransitions.drillDownExit
            } else {
                ExitTransition.None
            }
        },
        popEnterTransition = {
            if (isDrillDown(initialState)) {
                NavigationTransitions.drillDownPopEnter
            } else {
                EnterTransition.None
            }
        },
        popExitTransition = {
            if (isDrillDown(initialState)) {
                NavigationTransitions.drillDownPopExit
            } else {
                ExitTransition.None
            }
        },
    ) {
        gatewayNavigationGraph(
            navController = navController
        )

        welcomeNavigationGraph(
            navController = navController
        )

        onboardingNavigationGraph(
            navController = navController
        )

        recipeDetailsNavigationGraph(
            navController = navController
        )

        filtersNavigationGraph(
            navController = navController
        )

        recipeSearchNavigationGraph(
            navController = navController
        )

        mainFlowRoute(
            navController = navController
        )
    }
}

private fun isDrillDown(navBackStackEntry: NavBackStackEntry): Boolean {
    val destination = navBackStackEntry.destination
    
    // Screens that are NOT drill-down (usually main tabs or entry screens)
    val rootRoutes = listOf(
        NavigationDirection.Gateway::class,
        NavigationDirection.Welcome::class,
        NavigationDirection.Onboarding::class,
    ) + MainTab.entries.map { it.route::class }

    return rootRoutes.none { destination.hasRoute(it) }
}
