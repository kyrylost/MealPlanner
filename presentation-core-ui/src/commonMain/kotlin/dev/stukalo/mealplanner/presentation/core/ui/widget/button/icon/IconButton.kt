package dev.stukalo.mealplanner.presentation.core.ui.widget.button.icon

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonAnimation
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonColor
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.IconButtonSize
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconPreview16
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.StateIconButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.icon.core.IconButtonDefault
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.icon.core.iconButtonDefaultColorSet

/**
 * A composable function that represents a customizable icon button with optional loading state and interactive behavior.
 *
 * This function creates an icon button that allows for customizations such as size, appearance, and interaction handling.
 * It includes optional support for a loading state, custom colors, and animations. The button can be enabled or disabled,
 * and provides a callback when clicked.
 *
 * @param modifier The modifier to be applied to the button. Default is [Modifier].
 * @param icon The resource ID of the icon to be displayed inside the button.
 * @param onClick The callback to be invoked when the button is clicked. If `null`, the button is not clickable.
 * @param enabled A boolean indicating whether the button is enabled. Default is `true`, meaning the button is enabled.
 * @param isLoading A boolean indicating whether the button is in a loading state. Default is `false`, meaning no loading state.
 * @param colors The [ButtonColor] instance that provides colors for the button in different states (enabled, disabled, etc.).
 *               Default is provided by [IconButtonDefault.buttonColor].
 * @param sizes The [IconButtonSize] instance that defines the size configuration for the button.
 * @param corner The corner radius of the button. Default is provided by [IconButtonDefault.corner].
 * @param animation The [ButtonAnimation] instance that defines animation configurations for the button.
 *                  Default is provided by [IconButtonDefault.animation].
 * @param interactionSource The [MutableInteractionSource] for tracking the button's interaction states.
 *                          Default is a new [MutableInteractionSource] created with [remember].
 */
@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    colors: ButtonColor = IconButtonDefault.buttonColor(iconButtonDefaultColorSet()),
    sizes: IconButtonSize,
    corner: Dp = IconButtonDefault.corner(),
    animation: ButtonAnimation = IconButtonDefault.animation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    StateIconButton(
        onClick = onClick ?: {},
        icon = icon,
        colors = colors,
        sizes = sizes,
        corner = corner,
        animation = animation,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        isLoading = isLoading
    )
}

@Preview
@Composable
private fun PreviewIconButton() {
    Theme {
        IconButton(
            icon = IconPreview16,
            sizes = IconButtonDefault.buttonSizeSet().default()
        )
    }
}
