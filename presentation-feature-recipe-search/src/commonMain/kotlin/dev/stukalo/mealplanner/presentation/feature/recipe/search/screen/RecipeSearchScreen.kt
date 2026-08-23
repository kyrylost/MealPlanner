package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationKeys
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewIntent
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RecipeSearchScreen(navController: NavController) {
    val viewModel: RecipeSearchViewModel = koinViewModel()
    val recipes = viewModel.recipes.collectAsLazyPagingItems()

    // Observe result from FiltersScreen
    val filterResultState =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<String?>(NavigationKeys.FILTER_RESULT, null)
            ?.collectAsState()

    LaunchedEffect(filterResultState?.value) {
        filterResultState?.value?.let { json ->
            val filterModel = Json.decodeFromString<FilterDomainModel>(json)
            viewModel.onIntent(ViewIntent.ApplyFilters(filterModel))
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>(NavigationKeys.FILTER_RESULT)
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
                is ViewEvent.NavigateBack -> {
                    navController.popBackStack()
                }
                is ViewEvent.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(getString(event.message))
                    }
                }
            }
        }
    ) { state ->
        RecipeSearchContent(
            state = state,
            recipes = recipes,
            snackbarHostState = snackbarHostState,
            onIntent = viewModel::onIntent
        )
    }
}
