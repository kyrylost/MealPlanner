package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewIntent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeDetailsScreen(recipeId: String, onBackClick: () -> Unit) {
    val viewModel: RecipeDetailsViewModel = koinViewModel()

    LaunchedEffect(recipeId) {
        viewModel.onIntent(ViewIntent.LoadRecipe(recipeId))
    }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
            }
        }
    ) { state ->
        RecipeDetailsContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}
