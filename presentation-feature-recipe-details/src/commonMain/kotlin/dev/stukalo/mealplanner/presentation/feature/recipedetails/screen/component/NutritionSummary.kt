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
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.core.localization.common_unit_grams
import dev.stukalo.mealplanner.core.localization.common_unit_kcal
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(Theme.spacing.space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NutritionItem(
                label = stringResource(Res.string.common_calories),
                value = "${calories.toInt()}",
                unit = stringResource(Res.string.common_unit_kcal)
            )
            NutritionItem(
                label = stringResource(Res.string.common_proteins),
                value = "${protein.toInt()}",
                unit = stringResource(Res.string.common_unit_grams)
            )
            NutritionItem(
                label = stringResource(Res.string.common_fats),
                value = "${fats.toInt()}",
                unit = stringResource(Res.string.common_unit_grams)
            )
            NutritionItem(
                label = stringResource(Res.string.common_carbs),
                value = "${carbs.toInt()}",
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
            text = "$label ($unit)",
            style = Theme.typography.regular12,
            color = Theme.color.textSecondary
        )
    }
}
