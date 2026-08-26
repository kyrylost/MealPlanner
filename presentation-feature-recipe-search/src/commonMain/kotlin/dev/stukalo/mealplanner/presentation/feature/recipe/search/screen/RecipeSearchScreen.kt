package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationKeys
import dev.stukalo.mealplanner.presentation.core.navigation.ext.getSerializableState
import dev.stukalo.mealplanner.presentation.core.navigation.model.FilterNavModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.recipe.search.core.mapper.FilterNavMapper
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewState
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.viewmodel.koinViewModel

/**
 * The main entry point for the Recipe Search feature.
 * Handles navigation and links the [RecipeSearchViewModel] to the UI.
 *
 * @param navController The navigation controller used for routing.
 */
@Composable
internal fun RecipeSearchScreen(navController: NavController) {
    val viewModel: RecipeSearchViewModel = koinViewModel()
    val recipes = viewModel.recipes.collectAsLazyPagingItems()

    // Observe result from FiltersScreen
    val filterResultState =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getSerializableState<FilterNavModel>(NavigationKeys.FILTER_RESULT)

    LaunchedEffect(filterResultState?.value) {
        filterResultState?.value?.let { navModel ->
            val filters = FilterNavMapper.mapFrom(navModel)
            viewModel.onIntent(ViewIntent.ApplyFilters(filters))
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>(NavigationKeys.FILTER_RESULT)
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.NavigateToFilters -> {
                    val navModel = event.filters?.let(FilterNavMapper::mapTo)
                    navController.navigate(NavigationDirection.Filters(navModel))
                }
                is ViewEvent.NavigateToRecipeDetails -> {
                    navController.navigate(NavigationDirection.RecipeDetails(event.recipeId))
                }
                is ViewEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        },
        snackbarHostState = snackbarHostState
    ) { state ->
        RecipeSearchContent(
            state = state,
            recipes = recipes,
            snackbarHostState = snackbarHostState,
            onIntent = viewModel::onIntent
        )
    }
}

@Preview
@Composable
private fun RecipeSearchScreenPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            RecipeSearchContent(
                state = ViewState(),
                recipes = flowOf(PagingData.from(emptyList<RecipeDomainModel>())).collectAsLazyPagingItems(),
                snackbarHostState = SnackbarHostState(),
                onIntent = {}
            )
        }
    }
}
