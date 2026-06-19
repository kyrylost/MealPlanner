package dev.stukalo.mealplanner.presentation.feature.main.navigation.ext

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.ext.safeNavigation

internal fun NavHostController.navigateTab(route: NavigationDirection) {
    safeNavigation(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
