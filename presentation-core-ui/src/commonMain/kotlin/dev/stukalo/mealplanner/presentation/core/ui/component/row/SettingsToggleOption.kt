package dev.stukalo.mealplanner.presentation.core.ui.component.row

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun SettingsToggleOption(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    enabled: Boolean = true
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.background.secondary)
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
            .padding(Theme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = Theme.typography.regular14,
                color = Theme.color.text.primary
            )
            if (description != null) {
                Text(
                    text = description,
                    style = Theme.typography.regular12,
                    color = Theme.color.text.secondary
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Theme.color.brand.primary,
                checkedTrackColor = Theme.color.brand.primary.copy(alpha = 0.5f),
                uncheckedThumbColor = Theme.color.text.secondary,
                uncheckedTrackColor = Theme.color.background.primary
            )
        )
    }
}

@Preview
@Composable
private fun SettingsToggleOptionPreview() {
    Theme {
        SettingsToggleOption(
            title = "Toggle Option",
            description = "Description of the option",
            checked = true,
            onCheckedChange = {}
        )
    }
}
