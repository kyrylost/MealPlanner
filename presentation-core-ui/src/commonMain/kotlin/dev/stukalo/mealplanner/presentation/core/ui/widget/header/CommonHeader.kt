package dev.stukalo.mealplanner.presentation.core.ui.widget.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.barcode_scanner_title
import dev.stukalo.mealplanner.core.localization.common_back
import dev.stukalo.mealplanner.core.localization.common_settings
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconSettings
import org.jetbrains.compose.resources.stringResource

/**
 * A common header component used across different screens.
 *
 * @param title The title text to display in the header.
 * @param modifier The modifier to be applied to the header.
 * @param leftIcon An optional icon to display on the left side of the header.
 * @param leftIconTint The tint color for the left icon.
 * @param leftIconContentDescription The content description for the left icon.
 * @param onLeftIconClick The callback to be invoked when the left icon is clicked.
 * @param rightIcon An optional icon to display on the right side of the header.
 * @param rightIconTint The tint color for the right icon.
 * @param rightIconContentDescription The content description for the right icon.
 * @param onRightIconClick The callback to be invoked when the right icon is clicked.
 */
@Composable
fun CommonHeader(
    title: String,
    titleColor: Color = Theme.color.text.primary,
    leftIcon: ImageVector? = null,
    leftIconTint: Color = Theme.color.icon.primary,
    leftIconContentDescription: String? = null,
    onLeftIconClick: (() -> Unit)? = null,
    rightIcon: ImageVector? = null,
    rightIconTint: Color = Theme.color.icon.primary,
    rightIconContentDescription: String? = null,
    onRightIconClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val iconClickableAreaSize = Theme.size.clickableIconArea

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                vertical = Theme.spacing.space8,
                horizontal = Theme.spacing.space16
            )
    ) {
        if (leftIcon != null && onLeftIconClick != null) {
            IconButton(
                onClick = onLeftIconClick,
                modifier =
                Modifier
                    .size(iconClickableAreaSize)
            ) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = leftIconContentDescription,
                    tint = leftIconTint,
                    modifier =
                    Modifier
                        .padding(Theme.spacing.space8)
                )
            }

            Spacer(modifier = Modifier.width(Theme.spacing.space8))
        }

        Text(
            text = title,
            style = Theme.typography.bold16,
            color = titleColor,
            textAlign = TextAlign.Start,
            modifier =
            Modifier
                .weight(1f)
        )

        if (rightIcon != null && onRightIconClick != null) {
            IconButton(
                onClick = onRightIconClick,
                modifier =
                Modifier
                    .size(iconClickableAreaSize)
            ) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = rightIconContentDescription,
                    tint = rightIconTint,
                    modifier =
                    Modifier
                        .padding(Theme.spacing.space8)
                )
            }
        }
    }
}

@Preview
@Composable
private fun CommonHeaderPreview() {
    Theme {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .background(Theme.color.background.primary)
        ) {
            CommonHeader(
                title = stringResource(Res.string.barcode_scanner_title),
                leftIcon = IconBack,
                leftIconContentDescription = stringResource(Res.string.common_back),
                onLeftIconClick = {},
                rightIcon = IconSettings,
                rightIconContentDescription = stringResource(Res.string.common_settings),
                onRightIconClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun CommonHeaderNoLeftIconPreview() {
    Theme {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .background(Theme.color.background.primary)
        ) {
            CommonHeader(
                title = stringResource(Res.string.barcode_scanner_title),
                rightIcon = IconSettings,
                rightIconContentDescription = stringResource(Res.string.common_settings),
                onRightIconClick = {}
            )
        }
    }
}
