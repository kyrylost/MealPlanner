package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewIntent
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

/**
 * The entry point for the Home screen.
 *
 * This screen sets up the [HomeViewModel] and handles navigation events.
 *
 * @param onNavigateToRecipeDetails Callback to navigate to recipe details.
 * @param onNavigateToRecipeSearch Callback to navigate to recipe search.
 */
@Composable
internal fun HomeScreen(onNavigateToRecipeDetails: (String) -> Unit, onNavigateToRecipeSearch: () -> Unit) {
    val viewModel: HomeViewModel = koinViewModel()
    val recommendedRecipes = viewModel.recommendedRecipes.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }

    LifecycleResumeEffect(Unit) {
        viewModel.onIntent(ViewIntent.OnResume)
        onPauseOrDispose {}
    }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.NavigateToRecipeDetails -> onNavigateToRecipeDetails(event.recipeId)
                ViewEvent.NavigateToRecipeSearch -> onNavigateToRecipeSearch()
                is ViewEvent.ShowError -> {
                    snackbarHostState.showSnackbar(getString(event.message))
                }
            }
        },
        content = { state ->
            HomeContent(
                state = state,
                recommendedRecipes = recommendedRecipes,
                snackbarHostState = snackbarHostState,
                onIntent = viewModel::onIntent
            )
        }
    )
}
