package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_settings
import dev.stukalo.mealplanner.core.localization.statistics_meal_tracking
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconSettings
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.component.MealDetailsDialog
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.component.MealTrackingItem
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun StatisticsContent(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.color.background)
    ) {
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .align(Alignment.End)
                .statusBarsPadding()
                .padding(Theme.spacing.space16)
        ) {
            Icon(
                imageVector = IconSettings,
                contentDescription = stringResource(Res.string.common_settings),
                tint = Theme.color.iconPrimary
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
        ) {
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
    }
}

@Preview
@Composable
private fun StatisticsContentPreview() {
    Theme {
        StatisticsContent(
            state = ViewState(
                meals = listOf(
                    MealSlotProgress(1, "Breakfast", 500.0, 30.0, 15.0, 60.0, true),
                    MealSlotProgress(2, "Lunch", 800.0, 40.0, 25.0, 100.0, false),
                    MealSlotProgress(3, "Dinner", 700.0, 35.0, 20.0, 80.0, false),
                )
            ),
            onIntent = {},
            onSettingsClick = {}
        )
    }
}
