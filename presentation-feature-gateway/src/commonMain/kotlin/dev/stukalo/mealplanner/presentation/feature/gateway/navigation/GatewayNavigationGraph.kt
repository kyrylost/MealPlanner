package dev.stukalo.mealplanner.presentation.feature.gateway.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.ext.safeNavigation
import dev.stukalo.mealplanner.presentation.feature.gateway.screen.GatewayScreen

fun NavGraphBuilder.gatewayNavigationGraph(navController: NavHostController) {
    composable<NavigationDirection.Gateway> {
        GatewayScreen(
            onNavigateToMain = {
                navController.safeNavigation(NavigationDirection.Home) {
                    popUpTo(NavigationDirection.Gateway) { inclusive = true }
                }
            },
            onNavigateToWelcome = {
                navController.safeNavigation(NavigationDirection.Welcome) {
                    popUpTo(NavigationDirection.Gateway) { inclusive = true }
                }
            }
        )
    }
}
