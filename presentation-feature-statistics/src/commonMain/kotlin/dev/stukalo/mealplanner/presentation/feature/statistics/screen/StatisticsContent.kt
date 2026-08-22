package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.core.localization.common_statistics
import dev.stukalo.mealplanner.core.localization.common_value_placeholder
import dev.stukalo.mealplanner.core.localization.statistics_add_weight
import dev.stukalo.mealplanner.core.localization.statistics_edit_time
import dev.stukalo.mealplanner.core.localization.statistics_edit_time_hint
import dev.stukalo.mealplanner.core.localization.statistics_meal_tracking
import dev.stukalo.mealplanner.core.localization.statistics_month
import dev.stukalo.mealplanner.core.localization.statistics_nutrition_statistics
import dev.stukalo.mealplanner.core.localization.statistics_week
import dev.stukalo.mealplanner.core.localization.statistics_weight_history
import dev.stukalo.mealplanner.core.localization.statistics_year
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.component.chart.ChartStyle
import dev.stukalo.mealplanner.presentation.core.ui.component.chart.StatisticsChart
import dev.stukalo.mealplanner.presentation.core.ui.component.dialog.ValueEditDialog
import dev.stukalo.mealplanner.presentation.core.ui.component.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.component.selector.SegmentedSelector
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.AppSnackbarHost
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconAdd
import dev.stukalo.mealplanner.presentation.core.ui.mapper.toText
import dev.stukalo.mealplanner.presentation.feature.statistics.component.MealDetailsDialog
import dev.stukalo.mealplanner.presentation.feature.statistics.component.MealTrackingItem
import dev.stukalo.mealplanner.presentation.feature.statistics.component.StreakCard
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

/**
 * Stateless UI content for the Statistics screen.
 * Displays nutrient charts, weight history, and meal tracking items.
 *
 * @param state The current UI state to render.
 * @param snackbarHostState State for showing snackbars.
 * @param onIntent Callback to propagate user actions back to the ViewModel.
 */
@Composable
internal fun StatisticsContent(state: ViewState, snackbarHostState: SnackbarHostState, onIntent: (ViewIntent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CommonHeader(
                title = stringResource(Res.string.common_statistics)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding =
                PaddingValues(
                    start = Theme.spacing.space16,
                    end = Theme.spacing.space16,
                    top = Theme.spacing.space16,
                    bottom = Theme.spacing.space16 + LocalBottomBarHeight.current
                ),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
            ) {
                item {
                    StreakCard(
                        streak = state.streak
                    )
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)) {
                        Text(
                            text = stringResource(Res.string.statistics_nutrition_statistics),
                            style = Theme.typography.bold16,
                            color = Theme.color.text.primary
                        )
                        SegmentedSelector(
                            items = StatisticsInterval.entries,
                            selectedItem = state.timeInterval,
                            onItemSelected = { onIntent(ViewIntent.ChangeTimeInterval(it)) },
                            label = { interval ->
                                stringResource(
                                    when (interval) {
                                        StatisticsInterval.WEEK -> Res.string.statistics_week
                                        StatisticsInterval.MONTH -> Res.string.statistics_month
                                        StatisticsInterval.YEAR -> Res.string.statistics_year
                                    }
                                )
                            }
                        )
                        SegmentedSelector(
                            items = PfcCategory.entries,
                            selectedItem = state.pfcCategory,
                            onItemSelected = { onIntent(ViewIntent.ChangePfcCategory(it)) },
                            label = { category ->
                                stringResource(
                                    when (category) {
                                        PfcCategory.CALORIES -> Res.string.common_calories
                                        PfcCategory.PROTEINS -> Res.string.common_proteins
                                        PfcCategory.FATS -> Res.string.common_fats
                                        PfcCategory.CARBOHYDRATES -> Res.string.common_carbs
                                    }
                                )
                            }
                        )
                        StatisticsChart(
                            points = state.pfcData,
                            style = ChartStyle.BAR
                        )
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(Res.string.statistics_weight_history),
                                style = Theme.typography.bold16,
                                color = Theme.color.text.primary
                            )
                            IconButton(onClick = { onIntent(ViewIntent.OnAddWeightClick) }) {
                                Icon(
                                    imageVector = IconAdd,
                                    contentDescription = null,
                                    tint = Theme.color.brand.primary
                                )
                            }
                        }
                        SegmentedSelector(
                            items = StatisticsInterval.entries,
                            selectedItem = state.weightInterval,
                            onItemSelected = { onIntent(ViewIntent.ChangeWeightInterval(it)) },
                            label = { interval ->
                                stringResource(
                                    when (interval) {
                                        StatisticsInterval.WEEK -> Res.string.statistics_week
                                        StatisticsInterval.MONTH -> Res.string.statistics_month
                                        StatisticsInterval.YEAR -> Res.string.statistics_year
                                    }
                                )
                            }
                        )
                        StatisticsChart(
                            points = state.weightData,
                            style = ChartStyle.LINE,
                            targetValue = state.targetWeight
                        )
                    }
                }

                item {
                    Text(
                        text = stringResource(Res.string.statistics_meal_tracking),
                        style = Theme.typography.bold16,
                        color = Theme.color.text.primary,
                        modifier = Modifier.padding(bottom = Theme.spacing.space8)
                    )
                }

                items(state.meals) { meal ->
                    MealTrackingItem(
                        name = stringResource(meal.type.toText()),
                        calories = meal.calories,
                        isConsumed = meal.isConsumed,
                        onConsumedClick = { onIntent(ViewIntent.OnMealConsumed(meal.id)) },
                        onMealClick = { onIntent(ViewIntent.OnMealClick(meal)) },
                        onEditTimeClick = { onIntent(ViewIntent.OnEditTimeClick(meal.id, meal.startTime)) }
                    )
                }
            }
        }

        state.editingSlotId?.let { slotId ->
            ValueEditDialog(
                initialValue = state.editingSlotTime?.toString()?.substring(0, 5).orEmpty(),
                onDismissRequest = { onIntent(ViewIntent.OnDismissTimePickerDialog) },
                onConfirm = { value ->
                    try {
                        val parts = value.split(":")
                        if (parts.size == 2) {
                            val hour = parts[0].toInt()
                            val minute = parts[1].toInt()
                            onIntent(ViewIntent.OnTimeSelected(slotId, LocalTime(hour, minute)))
                        }
                    } catch (_: Exception) {
                        // Ideally show a toast or error state
                    }
                },
                title = stringResource(Res.string.statistics_edit_time),
                message = stringResource(Res.string.statistics_edit_time_hint),
                placeholder = "HH:mm",
                confirmLabel = stringResource(Res.string.common_ok),
                dismissLabel = stringResource(Res.string.common_cancel)
            )
        }

        state.selectedMeal?.let { meal ->
            MealDetailsDialog(
                meal = meal,
                onDismissRequest = { onIntent(ViewIntent.OnDismissDialog) }
            )
        }

        if (state.isAddWeightDialogVisible) {
            ValueEditDialog(
                initialValue = "",
                onDismissRequest = { onIntent(ViewIntent.OnDismissAddWeightDialog) },
                onConfirm = { value ->
                    value.toDoubleOrNull()?.let {
                        onIntent(ViewIntent.OnAddWeight(it))
                    }
                },
                title = stringResource(Res.string.statistics_add_weight),
                placeholder = stringResource(Res.string.common_value_placeholder),
                confirmLabel = stringResource(Res.string.common_ok),
                dismissLabel = stringResource(Res.string.common_cancel),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        AppSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
private fun StatisticsContentPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
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
                        ),
                        MealSlotProgress(
                            id = 3,
                            type = MealTypeDomainModel.DINNER,
                            startTime = LocalTime(19, 0),
                            calories = 700.0,
                            proteins = 35.0,
                            fats = 20.0,
                            carbohydrates = 80.0,
                            isConsumed = false
                        )
                    )
                ),
                snackbarHostState = remember { SnackbarHostState() },
                onIntent = {}
            )
        }
    }
}
