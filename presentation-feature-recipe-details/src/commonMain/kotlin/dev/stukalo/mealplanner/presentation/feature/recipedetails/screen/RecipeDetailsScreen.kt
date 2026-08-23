package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewIntent
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RecipeDetailsScreen(recipeId: String, onBackClick: () -> Unit) {
    val viewModel: RecipeDetailsViewModel = koinViewModel()

    LaunchedEffect(recipeId) {
        viewModel.onIntent(ViewIntent.LoadRecipe(recipeId))
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
                is ViewEvent.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(getString(event.message))
                    }
                }
            }
        }
    ) { state ->
        RecipeDetailsContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onIntent = viewModel::onIntent
        )
    }
}
