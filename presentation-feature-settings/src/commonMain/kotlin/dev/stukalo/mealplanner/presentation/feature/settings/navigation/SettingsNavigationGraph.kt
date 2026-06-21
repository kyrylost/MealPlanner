package dev.stukalo.mealplanner.presentation.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.settings.screen.SettingsScreen

fun NavGraphBuilder.settingsNavigationGraph(
    navController: NavHostController,
) {
    composable<NavigationDirection.Settings> {
        SettingsScreen(
            onBackClick = { navController.popBackStack() }
        )
    }
}
