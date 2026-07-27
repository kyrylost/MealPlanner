package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
) {
    val viewModel: SettingsViewModel = koinViewModel()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
            }
        }
    ) { state ->
        SettingsContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}
