package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * Logic holder for the Statistics screen.
 * Handles single events like error messages and coordinates the UI state.
 */
@Composable
internal fun StatisticsScreen() {
    val viewModel: StatisticsViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = {},
        snackbarHostState = snackbarHostState,
        content = { state ->
            StatisticsContent(
                state = state,
                snackbarHostState = snackbarHostState,
                onIntent = viewModel::onIntent
            )
        }
    )
}
