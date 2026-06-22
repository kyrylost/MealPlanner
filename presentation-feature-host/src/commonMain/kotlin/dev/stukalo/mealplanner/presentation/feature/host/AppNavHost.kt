package dev.stukalo.mealplanner.presentation.feature.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.gateway.navigation.gatewayNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.main.navigation.mainFlowRoute
import dev.stukalo.mealplanner.presentation.feature.onboarding.navigation.onboardingNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.recipedetails.navigation.recipeDetailsNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.settings.navigation.settingsNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.welcome.navigation.welcomeNavigationGraph

@Composable
internal fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDirection.Gateway,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
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

        settingsNavigationGraph(
            navController = navController
        )

        mainFlowRoute(
            navController = navController
        )
    }
}
