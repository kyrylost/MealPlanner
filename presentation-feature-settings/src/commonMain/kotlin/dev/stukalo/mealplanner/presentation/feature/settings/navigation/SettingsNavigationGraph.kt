package dev.stukalo.mealplanner.presentation.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.settings.screen.SettingsScreen

/**
 * Extension for registering the Settings feature in the navigation graph.
 */
fun NavGraphBuilder.settingsNavigationGraph() {
    composable<NavigationDirection.Settings> {
        SettingsScreen()
    }
}
