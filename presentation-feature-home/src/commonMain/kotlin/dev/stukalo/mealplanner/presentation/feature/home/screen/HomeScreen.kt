package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewEvent
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
fun HomeScreen(onNavigateToRecipeDetails: (String) -> Unit, onNavigateToRecipeSearch: () -> Unit) {
    val viewModel: HomeViewModel = koinViewModel()
    val recommendedRecipes = viewModel.recommendedRecipes.collectAsLazyPagingItems()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.NavigateToRecipeDetails -> onNavigateToRecipeDetails(event.recipeId)
                ViewEvent.NavigateToRecipeSearch -> onNavigateToRecipeSearch()
            }
        },
        content = { state ->
            HomeContent(
                state = state,
                recommendedRecipes = recommendedRecipes,
                onIntent = viewModel::onIntent
            )
        }
    )
}
