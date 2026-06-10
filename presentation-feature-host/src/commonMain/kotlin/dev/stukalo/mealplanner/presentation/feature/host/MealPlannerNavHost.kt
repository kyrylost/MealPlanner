package dev.stukalo.mealplanner.presentation.feature.host

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.main.navigation.mainFlowRoute
import dev.stukalo.mealplanner.presentation.feature.onboarding.navigation.onboardingNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.navigation.barcodeScannerNavigationGraph
import dev.stukalo.mealplanner.presentation.feature.welcome.navigation.welcomeNavigationGraph
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MealPlannerNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationDirection.Welcome,
    ) {
        welcomeNavigationGraph(
            navController = navController
        )

        onboardingNavigationGraph(
            navController = navController
        )

        barcodeScannerNavigationGraph(
            navController = navController
        )

        mainFlowRoute(
            navController = navController
        )
    }
}
