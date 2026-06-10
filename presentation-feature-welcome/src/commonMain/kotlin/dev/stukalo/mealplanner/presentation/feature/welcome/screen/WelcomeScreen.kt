package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreen(
    onNavigateToMain: () -> Unit,
) {

    val viewModel: WelcomeViewModel = koinViewModel()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.ShowError -> Unit
                is ViewEvent.NavigateToMainScreen -> onNavigateToMain()
            }
        },
        content = { state ->
            WelcomeContent(
                state = state,
                onIntent = viewModel::onIntent
            )
        },
    )
}
