package dev.stukalo.mealplanner.presentation.feature.settings.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme

/**
 * A generic settings option component.
 *
 * @param title The title of the option.
 * @param onClick The callback for when the option is clicked.
 * @param modifier The modifier to apply to the component.
 */
@Composable
fun SettingsOption(title: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.backgroundSecondary)
            .clickable(onClick = onClick)
            .padding(Theme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        Text(
            text = title,
            style = Theme.typography.regular14,
            color = Theme.color.textPrimary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
private fun SettingsOptionPreview() {
    Theme {
        SettingsOption(
            title = "Meal Schedule",
            onClick = {}
        )
    }
}
