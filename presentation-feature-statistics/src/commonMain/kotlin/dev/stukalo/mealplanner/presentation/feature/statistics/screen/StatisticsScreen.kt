package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StatisticsScreen(
    onSettingsClick: () -> Unit,
    viewModel: StatisticsViewModel = koinViewModel()
) {
    MviScreen(
        viewModel = viewModel,
        onSingleEvent = {}
    ) { state ->
        StatisticsContent(
            state = state,
            onIntent = viewModel::onIntent,
            onSettingsClick = onSettingsClick
        )
    }
}
