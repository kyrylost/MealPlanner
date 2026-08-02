package dev.stukalo.mealplanner.presentation.feature.statistics.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.StatisticsScreen

fun NavGraphBuilder.statisticsNavigationGraph() {
    composable<NavigationDirection.Statistics> {
        StatisticsScreen()
    }
}
