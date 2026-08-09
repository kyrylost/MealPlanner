package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun HealthLabelChip(label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Theme.color.brand.primary.copy(alpha = 0.1f),
        shape = Theme.shape.normalRoundedCornerShape
    ) {
        Text(
            text = label,
            style = Theme.typography.bold12,
            color = Theme.color.brand.primary,
            modifier = Modifier.padding(horizontal = Theme.spacing.space12, vertical = Theme.spacing.space4)
        )
    }
}

@Preview
@Composable
private fun HealthLabelChipPreview() {
    Theme {
        HealthLabelChip(label = "Gluten-Free")
    }
}
