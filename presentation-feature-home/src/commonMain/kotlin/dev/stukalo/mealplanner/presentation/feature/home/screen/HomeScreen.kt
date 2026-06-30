package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onNavigateToRecipeDetails: (String) -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.NavigateToRecipeDetails -> onNavigateToRecipeDetails(event.recipeId)
            }
        },
        content = { state ->
            HomeContent(
                state = state,
                onIntent = viewModel::onIntent
            )
        }
    )
}
