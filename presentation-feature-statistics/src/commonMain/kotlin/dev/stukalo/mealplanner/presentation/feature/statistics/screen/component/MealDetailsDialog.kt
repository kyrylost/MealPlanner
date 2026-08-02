package dev.stukalo.mealplanner.presentation.feature.statistics.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_close
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_grams_value
import dev.stukalo.mealplanner.core.localization.common_kcal
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.text.TextButton
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.MealSlotProgress
import org.jetbrains.compose.resources.stringResource

/**
 * A dialog displaying detailed information about a meal slot.
 *
 * @param meal The meal slot progress to display.
 * @param onDismissRequest The callback for dismissing the dialog.
 */
@Composable
fun MealDetailsDialog(meal: MealSlotProgress, onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = meal.name,
                style = Theme.typography.bold16,
                color = Theme.color.textPrimary
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing.space12)
            ) {
                NutrientRow(
                    label = stringResource(Res.string.common_calories),
                    value = stringResource(Res.string.common_kcal, meal.calories.toInt())
                )
                NutrientRow(
                    label = stringResource(Res.string.common_proteins),
                    value = stringResource(Res.string.common_grams_value, meal.proteins.toInt())
                )
                NutrientRow(
                    label = stringResource(Res.string.common_fats),
                    value = stringResource(Res.string.common_grams_value, meal.fats.toInt())
                )
                NutrientRow(
                    label = stringResource(Res.string.common_carbs),
                    value = stringResource(Res.string.common_grams_value, meal.carbohydrates.toInt())
                )
            }
        },
        confirmButton = {
            TextButton(
                text = stringResource(Res.string.common_close),
                onClick = {
                    onDismissRequest()
                }
            )
        },
        containerColor = Theme.color.backgroundSecondary,
        shape = Theme.shape.normalRoundedCornerShape
    )
}

@Composable
private fun NutrientRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = Theme.typography.regular14,
            color = Theme.color.textSecondary
        )
        Text(
            text = value,
            style = Theme.typography.bold14,
            color = Theme.color.textPrimary
        )
    }
}

@Preview
@Composable
private fun MealDetailsDialogPreview() {
    Theme {
        MealDetailsDialog(
            meal =
            MealSlotProgress(
                id = 1,
                name = "Lunch",
                calories = 750.0,
                proteins = 35.0,
                fats = 25.0,
                carbohydrates = 90.0,
                isConsumed = false
            ),
            onDismissRequest = {}
        )
    }
}
