package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_breakfast
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_dinner
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_lunch
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.core.localization.common_statistics
import dev.stukalo.mealplanner.core.localization.common_value_placeholder
import dev.stukalo.mealplanner.core.localization.statistics_add_weight
import dev.stukalo.mealplanner.core.localization.statistics_meal_tracking
import dev.stukalo.mealplanner.core.localization.statistics_month
import dev.stukalo.mealplanner.core.localization.statistics_nutrition_statistics
import dev.stukalo.mealplanner.core.localization.statistics_week
import dev.stukalo.mealplanner.core.localization.statistics_weight_history
import dev.stukalo.mealplanner.core.localization.statistics_year
import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconAdd
import dev.stukalo.mealplanner.presentation.core.ui.widget.chart.ChartStyle
import dev.stukalo.mealplanner.presentation.core.ui.widget.chart.StatisticsChart
import dev.stukalo.mealplanner.presentation.core.ui.widget.dialog.ValueEditDialog
import dev.stukalo.mealplanner.presentation.core.ui.widget.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.widget.selector.SegmentedSelector
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.component.MealDetailsDialog
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.component.MealTrackingItem
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.component.StreakCard
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun StatisticsContent(state: ViewState, onIntent: (ViewIntent) -> Unit) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
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
                        color = Theme.color.textPrimary
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
                            color = Theme.color.textPrimary
                        )
                        IconButton(onClick = { onIntent(ViewIntent.OnAddWeightClick) }) {
                            Icon(
                                imageVector = IconAdd,
                                contentDescription = null,
                                tint = Theme.color.primary
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
                    color = Theme.color.textPrimary,
                    modifier = Modifier.padding(bottom = Theme.spacing.space8)
                )
            }

            items(state.meals) { meal ->
                MealTrackingItem(
                    name = meal.name,
                    calories = meal.calories,
                    isConsumed = meal.isConsumed,
                    onConsumedClick = { onIntent(ViewIntent.OnMealConsumed(meal.id)) },
                    onMealClick = { onIntent(ViewIntent.OnMealClick(meal)) }
                )
            }
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
    }
}

@Preview
@Composable
private fun StatisticsContentPreview() {
    Theme {
        Surface(color = Theme.color.background) {
            StatisticsContent(
                state =
                ViewState(
                    meals =
                    listOf(
                        MealSlotProgress(1, stringResource(Res.string.common_breakfast), 500.0, 30.0, 15.0, 60.0, true),
                        MealSlotProgress(2, stringResource(Res.string.common_lunch), 800.0, 40.0, 25.0, 100.0, false),
                        MealSlotProgress(3, stringResource(Res.string.common_dinner), 700.0, 35.0, 20.0, 80.0, false)
                    )
                ),
                onIntent = {}
            )
        }
    }
}
