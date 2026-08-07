package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_breakfast
import dev.stukalo.mealplanner.core.localization.common_lunch
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel = koinViewModel()) {
    MviScreen(
        viewModel = viewModel,
        onSingleEvent = {}
    ) { state ->
        StatisticsContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    Theme {
        StatisticsContent(
            state = ViewState(
                meals = listOf(
                    MealSlotProgress(1, stringResource(Res.string.common_breakfast), 500.0, 30.0, 15.0, 60.0, true),
                    MealSlotProgress(2, stringResource(Res.string.common_lunch), 800.0, 40.0, 25.0, 100.0, false)
                )
            ),
            onIntent = {}
        )
    }
}
