package dev.stukalo.mealplanner.presentation.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.settings.screen.SettingsScreen

fun NavGraphBuilder.settingsNavigationGraph() {
    composable<NavigationDirection.Settings> {
        SettingsScreen()
    }
}
