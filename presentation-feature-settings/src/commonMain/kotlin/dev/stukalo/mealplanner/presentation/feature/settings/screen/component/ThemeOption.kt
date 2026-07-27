package dev.stukalo.mealplanner.presentation.feature.settings.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.ThemeColorPalette
import dev.stukalo.mealplanner.presentation.core.styling.color.toPrimaryColor

@Composable
fun ThemeOption(
    palette: ColorPaletteDomainModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val themeColorPalette = when (palette) {
        ColorPaletteDomainModel.ORANGE -> ThemeColorPalette.ORANGE
        ColorPaletteDomainModel.GREEN -> ThemeColorPalette.GREEN
    }
    val primary = themeColorPalette.toPrimaryColor()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.backgroundSecondary)
            .clickable(onClick = onClick)
            .padding(Theme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(primary)
        )
        Text(
            text = palette.name.lowercase().replaceFirstChar { it.uppercase() },
            style = Theme.typography.regular14,
            color = Theme.color.textPrimary
        )
    }
}
