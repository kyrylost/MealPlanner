package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.getString
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
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.ShowError -> {
                    snackbarHostState.showSnackbar(getString(event.message))
                }
            }
        },
        content = { state ->
            StatisticsContent(
                state = state,
                snackbarHostState = snackbarHostState,
                onIntent = viewModel::onIntent
            )
        }
    )
}

@Preview
@Composable
private fun StatisticsScreenPreview() {
    Theme {
        StatisticsContent(
            state = ViewState(
                meals = listOf(
                    MealSlotProgress(
                        id = 1,
                        type = MealTypeDomainModel.BREAKFAST,
                        startTime = LocalTime(7, 0),
                        calories = 500.0,
                        proteins = 30.0,
                        fats = 15.0,
                        carbohydrates = 60.0,
                        isConsumed = true
                    ),
                    MealSlotProgress(
                        id = 2,
                        type = MealTypeDomainModel.LUNCH,
                        startTime = LocalTime(12, 0),
                        calories = 800.0,
                        proteins = 40.0,
                        fats = 25.0,
                        carbohydrates = 100.0,
                        isConsumed = false
                    )
                )
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onIntent = {}
        )
    }
}
