package dev.stukalo.mealplanner.presentation.feature.settings.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.settings_theme_green
import dev.stukalo.mealplanner.core.localization.settings_theme_lime
import dev.stukalo.mealplanner.core.localization.settings_theme_orange
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.ThemeColorPalette
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.toPrimaryColor
import org.jetbrains.compose.resources.stringResource

/**
 * A component representing a theme choice option.
 *
 * @param palette The color palette to display.
 * @param isSelected Whether the palette is currently selected.
 * @param onClick The callback for when the option is clicked.
 * @param modifier The modifier to apply to the component.
 */
@Composable
fun ThemeOption(
    palette: ColorPaletteDomainModel,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themeColorPalette =
        when (palette) {
            ColorPaletteDomainModel.ORANGE -> ThemeColorPalette.ORANGE
            ColorPaletteDomainModel.GREEN -> ThemeColorPalette.GREEN
            ColorPaletteDomainModel.LIME -> ThemeColorPalette.LIME
        }
    val primary = themeColorPalette.toPrimaryColor()

    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.background.secondary)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = Theme.thickness.thickness2,
                        color = Theme.color.brand.primary,
                        shape = Theme.shape.normalRoundedCornerShape
                    )
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(Theme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        Box(
            modifier =
            Modifier
                .size(Theme.size.clickableIconArea)
                .clip(CircleShape)
                .background(primary)
        )
        Text(
            text =
            when (palette) {
                ColorPaletteDomainModel.ORANGE -> stringResource(Res.string.settings_theme_orange)
                ColorPaletteDomainModel.GREEN -> stringResource(Res.string.settings_theme_green)
                ColorPaletteDomainModel.LIME -> stringResource(Res.string.settings_theme_lime)
            },
            style = Theme.typography.regular14,
            color = Theme.color.text.primary
        )
    }
}

@Preview
@Composable
private fun ThemeOptionPreview() {
    Theme {
        ThemeOption(
            palette = ColorPaletteDomainModel.ORANGE,
            isSelected = true,
            onClick = {}
        )
    }
}
