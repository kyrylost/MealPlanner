package dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonAnimation
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonColor
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonSize
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.StateButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core.PrimaryButtonDefault
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core.primaryButtonDefaultColorSet

/**
 * A composable function that represents a primary button with customizable properties and optional loading state.
 *
 * This function creates a primary button with predefined styles and supports customizations for
 * appearance, size, and interactions. It includes optional support for loading indicators and icons.
 *
 * @param modifier The modifier to be applied to the button.
 * @param text The text to display on the button.
 * @param onClick The callback to be invoked when the button is clicked. If `null`, the button is not clickable.
 * @param startIcon The resource ID of the icon to display at the start of the button, or `null` if no icon is needed.
 * @param endIcon The resource ID of the icon to display at the end of the button, or `null` if no icon is needed.
 * @param enabled A boolean indicating whether the button is enabled. Default is `true`.
 * @param isLoading A boolean indicating whether the button is in a loading state. Default is `false`.
 * @param colors The [ButtonColor] instance that provides colors for different button states.
 *               Default is provided by [PrimaryButtonDefault.buttonColor].
 * @param sizes The [ButtonSize] instance that provides size configurations for the button.
 * @param corner The corner radius of the button. Default is provided by [PrimaryButtonDefault.corner].
 * @param textStyle The style of the text displayed on the button. Default is [Theme.typography.regular12].
 * @param animation The [ButtonAnimation] instance that provides animation configurations for the button.
 *                  Default is provided by [PrimaryButtonDefault.animation].
 * @param interactionSource The [MutableInteractionSource] for the button to track interaction states.
 *                          Default is a new [MutableInteractionSource] created with [remember].
 */
@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    colors: ButtonColor = PrimaryButtonDefault.buttonColor(primaryButtonDefaultColorSet()),
    sizes: ButtonSize = PrimaryButtonDefault.buttonSizeSet().default(),
    corner: Dp = PrimaryButtonDefault.corner(),
    textStyle: TextStyle = Theme.typography.regular12,
    animation: ButtonAnimation = PrimaryButtonDefault.animation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    StateButton(
        text,
        onClick = onClick ?: {},
        startIcon,
        endIcon,
        colors,
        sizes,
        corner,
        textStyle,
        animation,
        modifier,
        enabled,
        interactionSource,
        isLoading,
    )
}

@Preview
@Composable
private fun PreviewPrimaryButton() {
    Theme {
        PrimaryButton(
            text = "Primary button",
            sizes = PrimaryButtonDefault.buttonSizeSet().default(),
            textStyle = PrimaryButtonDefault.typography().default(),
        )
    }
}

@Preview
@Composable
private fun PreviewTransparentPrimaryButton() {
    Theme {
        PrimaryButton(
            text = "Primary button",
            sizes = PrimaryButtonDefault.buttonSizeSet().small(),
            textStyle = PrimaryButtonDefault.typography().small(),
            colors = PrimaryButtonDefault.buttonColor(
                primaryButtonDefaultColorSet().copy(
                    backgroundColorDefault = Color.Transparent,
                ),
            ),
        )
    }
}
