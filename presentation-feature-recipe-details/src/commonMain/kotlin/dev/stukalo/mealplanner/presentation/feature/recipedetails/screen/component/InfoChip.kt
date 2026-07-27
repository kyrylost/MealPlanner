package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun InfoChip(
    text: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space4)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Theme.spacing.space16),
                tint = Theme.color.textSecondary
            )
        }
        Text(
            text = text,
            style = Theme.typography.regular14,
            color = Theme.color.textSecondary
        )
    }
}
