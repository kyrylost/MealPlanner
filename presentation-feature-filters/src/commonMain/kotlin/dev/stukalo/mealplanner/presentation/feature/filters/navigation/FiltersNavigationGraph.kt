package dev.stukalo.mealplanner.presentation.feature.filters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationKeys
import dev.stukalo.mealplanner.presentation.feature.filters.screen.FiltersScreen
import kotlinx.serialization.json.Json

fun NavGraphBuilder.filtersNavigationGraph(navController: NavHostController) {
    composable<NavigationDirection.Filters> {
        // Retrieve initial filters from savedStateHandle if any
        val initialFiltersJson = navController.previousBackStackEntry?.savedStateHandle?.get<String>(
            NavigationKeys.FILTER_RESULT
        )
        val initialFilters = initialFiltersJson?.let { Json.decodeFromString<FilterDomainModel>(it) }

        FiltersScreen(
            initialFilters = initialFilters,
            onApplyFilters = { filters ->
                val filtersJson = Json.encodeToString(filters)
                navController.previousBackStackEntry?.savedStateHandle?.set(NavigationKeys.FILTER_RESULT, filtersJson)
                navController.popBackStack()
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
