package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewIntent
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeSearchScreen(
    navController: NavController,
) {
    val viewModel: RecipeSearchViewModel = koinViewModel()
    val recipes = viewModel.recipes.collectAsLazyPagingItems()

    // Observe result from FiltersScreen
    val filterResultState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>("filter_result", null)
        ?.collectAsState()

    LaunchedEffect(filterResultState?.value) {
        filterResultState?.value?.let { json ->
            val filterModel = Json.decodeFromString<FilterDomainModel>(json)
            viewModel.onIntent(ViewIntent.ApplyFilters(filterModel))
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("filter_result")
        }
    }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.NavigateToFilters -> {
                    navController.navigate(NavigationDirection.Filters)
                }
                is ViewEvent.NavigateToRecipeDetails -> {
                    navController.navigate(NavigationDirection.RecipeDetails(event.recipeId))
                }
            }
        }
    ) { state ->
        RecipeSearchContent(
            state = state,
            recipes = recipes,
            onIntent = viewModel::onIntent
        )
    }
}
