package dev.stukalo.mealplanner.presentation.core.ui.widget.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconClock

@Composable
fun InfoChip(text: String, icon: ImageVector? = null, modifier: Modifier = Modifier) {
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
                tint = Theme.color.text.secondary
            )
        }
        Text(
            text = text,
            style = Theme.typography.regular14,
            color = Theme.color.text.secondary
        )
    }
}

@Preview
@Composable
private fun InfoChipPreview() {
    Theme {
        InfoChip(
            text = "25 min",
            icon = IconClock
        )
    }
}
