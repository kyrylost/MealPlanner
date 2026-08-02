package dev.stukalo.mealplanner.presentation.core.ui.widget.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconSettings

@Composable
fun CommonHeader(
    title: String,
    modifier: Modifier = Modifier,
    leftIcon: ImageVector? = null,
    leftIconTint: Color = Theme.color.iconPrimary,
    onLeftIconClick: (() -> Unit)? = null,
    rightIcon: ImageVector? = null,
    rightIconTint: Color = Theme.color.iconPrimary,
    onRightIconClick: (() -> Unit)? = null
) {
    val iconClickableAreaSize = 40.dp

    Box(
        contentAlignment = Alignment.Center,
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
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = leftIcon,
                    contentDescription = null,
                    tint = leftIconTint,
                    modifier =
                    Modifier
                        .padding(Theme.spacing.space8)
                )
            }
        }

        Text(
            text = title,
            style = Theme.typography.bold16,
            color = Theme.color.textPrimary,
            textAlign = TextAlign.Center,
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.space48)
        )

        if (rightIcon != null && onRightIconClick != null) {
            IconButton(
                onClick = onRightIconClick,
                modifier =
                Modifier
                    .size(iconClickableAreaSize)
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = null,
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
                .background(Theme.color.background)
        ) {
            CommonHeader(
                title = "Title",
                leftIcon = IconBack,
                onLeftIconClick = {},
                rightIcon = IconSettings,
                onRightIconClick = {}
            )
        }
    }
}
