package dev.devlight.skeleton.presentation.core.ui.component.widget.button.core

import androidx.compose.ui.graphics.Color

/**
 * Holds the color configuration for a button in different states.
 *
 * @property borderColorDisabled The border color when the button is disabled.
 * @property borderColorPressed The border color when the button is pressed.
 * @property borderColorDefault The default border color.
 * @property foregroundColorDisabled The foreground (icon/text) color when the button is disabled.
 * @property foregroundColorPressed The foreground color when the button is pressed.
 * @property foregroundColorDefault The default foreground color.
 * @property backgroundColorDisabled The background color when the button is disabled.
 * @property backgroundColorPressed The background color when the button is pressed.
 * @property backgroundColorDefault The default background color.
 */
data class ButtonColorSet(
    val borderColorDisabled: Color,
    val borderColorPressed: Color,
    val borderColorDefault: Color,
    val foregroundColorDisabled: Color,
    val foregroundColorPressed: Color,
    val foregroundColorDefault: Color,
    val backgroundColorDisabled: Color,
    val backgroundColorPressed: Color,
    val backgroundColorDefault: Color
)
