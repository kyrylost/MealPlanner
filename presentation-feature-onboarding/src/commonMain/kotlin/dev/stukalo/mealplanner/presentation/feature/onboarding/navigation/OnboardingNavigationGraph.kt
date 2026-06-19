package dev.stukalo.mealplanner.presentation.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.OnboardingScreen

fun NavGraphBuilder.onboardingNavigationGraph(
    navController: NavHostController,
) {
    composable<NavigationDirection.Onboarding> {
        OnboardingScreen()
    }
}
