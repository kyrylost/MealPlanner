package dev.stukalo.mealplanner.presentation.feature.statistics.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_kcal
import dev.stukalo.mealplanner.core.localization.common_lunch
import dev.stukalo.mealplanner.core.localization.statistics_mark_as_consumed
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconCheck
import org.jetbrains.compose.resources.stringResource

@Composable
fun MealTrackingItem(
    name: String,
    calories: Double,
    isConsumed: Boolean,
    onConsumedClick: () -> Unit,
    onMealClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .background(
                color = Theme.color.background.secondary,
                shape = Theme.shape.normalRoundedCornerShape
            ).clickable { onMealClick() }
            .padding(Theme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = Theme.typography.bold14,
                color = Theme.color.text.primary
            )
            Text(
                text = stringResource(Res.string.common_kcal, calories.toInt()),
                style = Theme.typography.regular12,
                color = Theme.color.text.secondary
            )
        }

        IconButton(
            onClick = onConsumedClick,
            enabled = !isConsumed
        ) {
            Icon(
                imageVector = IconCheck,
                contentDescription = stringResource(Res.string.statistics_mark_as_consumed),
                tint = if (isConsumed) Theme.color.brand.primary else Theme.color.icon.primary,
                modifier = Modifier.size(Theme.spacing.space32)
            )
        }
    }
}

@Preview
@Composable
private fun MealTrackingItemPreview() {
    Theme {
        MealTrackingItem(
            name = stringResource(Res.string.common_lunch),
            calories = 750.0,
            isConsumed = false,
            onConsumedClick = {},
            onMealClick = {}
        )
    }
}
