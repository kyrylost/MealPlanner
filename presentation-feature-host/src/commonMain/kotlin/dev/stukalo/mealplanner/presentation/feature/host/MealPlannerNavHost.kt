package dev.stukalo.mealplanner.presentation.feature.host

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.gateway.navigation.gatewayNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.main.navigation.mainFlowRoute
import dev.stukalo.mealplanner.presentation.feature.onboarding.navigation.onboardingNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.recipedetails.navigation.recipeDetailsNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.welcome.navigation.welcomeNavigationGraph

@Preview
@Composable
fun MealPlannerNavHost() {

    val navController = rememberNavController()

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

        mainFlowRoute(
            navController = navController
        )
    }
}
