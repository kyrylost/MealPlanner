package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreen(onNavigateToMain: () -> Unit) {
    val viewModel: WelcomeViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.NavigateToMainScreen -> onNavigateToMain()
            }
        },
        snackbarHostState = snackbarHostState,
        content = { state ->
            WelcomeContent(
                state = state,
                snackbarHostState = snackbarHostState,
                onIntent = viewModel::onIntent
            )
        }
    )
}
