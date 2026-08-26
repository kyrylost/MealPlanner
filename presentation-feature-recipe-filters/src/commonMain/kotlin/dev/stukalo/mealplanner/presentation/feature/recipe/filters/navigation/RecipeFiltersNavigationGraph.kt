package dev.stukalo.mealplanner.presentation.feature.recipe.filters.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationKeys
import dev.stukalo.mealplanner.presentation.core.navigation.ext.setSerializable
import dev.stukalo.mealplanner.presentation.core.navigation.model.FilterNavModel
import dev.stukalo.mealplanner.presentation.core.navigation.util.SerializableNavType
import dev.stukalo.mealplanner.presentation.feature.recipe.common.core.mapper.FilterNavMapper
import dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen.RecipeFiltersScreen
import kotlin.reflect.typeOf

/**
 * Registers the Recipe Filters feature in the navigation graph.
 *
 * @param navController The navigation controller used for routing and result handling.
 */
fun NavGraphBuilder.recipeFiltersNavigationGraph(navController: NavHostController) {
    composable<NavigationDirection.Filters>(
        typeMap = mapOf(
            typeOf<FilterNavModel?>() to SerializableNavType(FilterNavModel.serializer(), isNullable = true)
        )
    ) { backStackEntry ->
        val route: NavigationDirection.Filters = backStackEntry.toRoute()
        val initialFilters = route.initialFilters?.let(FilterNavMapper::mapFrom)

        RecipeFiltersScreen(
            initialFilters = initialFilters,
            onApplyFilters = { filters ->
                val navModel = FilterNavMapper.mapTo(filters)
                navController.previousBackStackEntry?.savedStateHandle?.setSerializable(
                    NavigationKeys.FILTER_RESULT,
                    navModel
                )
                navController.popBackStack()
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
