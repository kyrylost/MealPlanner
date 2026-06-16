package dev.stukalo.mealplanner.presentation.core.ui.widget.button.text

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonAnimation
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonColor
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonSize
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.StateButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.text.core.TextButtonDefault
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.text.core.textButtonDefaultColorSet

/**
 * A composable function that represents a text button with customizable properties.
 */
@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (() -> Unit)? = null,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    colors: ButtonColor = TextButtonDefault.buttonColor(textButtonDefaultColorSet()),
    sizes: ButtonSize = TextButtonDefault.buttonSizeSet().default(),
    corner: Dp = TextButtonDefault.corner(),
    textStyle: TextStyle = Theme.typography.regular14,
    animation: ButtonAnimation = TextButtonDefault.animation(),
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
private fun PreviewTextButton() {
    Theme {
        TextButton(
            text = "Text button",
        )
    }
}
