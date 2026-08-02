package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_grams_value
import dev.stukalo.mealplanner.core.localization.common_kcal
import dev.stukalo.mealplanner.core.localization.common_nutrient_with_unit
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.core.localization.common_unit_grams
import dev.stukalo.mealplanner.core.localization.common_unit_kcal
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.widget.card.BlurredCard
import org.jetbrains.compose.resources.stringResource

@Composable
fun NutritionSummary(
    calories: Float,
    protein: Float,
    fats: Float,
    carbs: Float,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    BlurredCard(
        modifier = modifier.fillMaxWidth(),
        shape = Theme.shape.normalRoundedCornerShape,
        hazeState = hazeState
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(Theme.spacing.space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NutritionItem(
                label = stringResource(Res.string.common_calories),
                value = stringResource(Res.string.common_kcal, calories.toInt()),
                unit = stringResource(Res.string.common_unit_kcal)
            )
            NutritionItem(
                label = stringResource(Res.string.common_proteins),
                value = stringResource(Res.string.common_grams_value, protein.toInt()),
                unit = stringResource(Res.string.common_unit_grams)
            )
            NutritionItem(
                label = stringResource(Res.string.common_fats),
                value = stringResource(Res.string.common_grams_value, fats.toInt()),
                unit = stringResource(Res.string.common_unit_grams)
            )
            NutritionItem(
                label = stringResource(Res.string.common_carbs),
                value = stringResource(Res.string.common_grams_value, carbs.toInt()),
                unit = stringResource(Res.string.common_unit_grams)
            )
        }
    }
}

@Composable
private fun NutritionItem(label: String, value: String, unit: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = Theme.typography.bold16,
            color = Theme.color.textPrimary
        )
        Text(
            text = stringResource(Res.string.common_nutrient_with_unit, label, unit),
            style = Theme.typography.regular12,
            color = Theme.color.textSecondary
        )
    }
}

@Preview
@Composable
private fun NutritionSummaryPreview() {
    Theme {
        NutritionSummary(
            calories = 450f,
            protein = 35f,
            fats = 20f,
            carbs = 15f,
            hazeState = rememberHazeState()
        )
    }
}
