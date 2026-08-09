package dev.stukalo.mealplanner.presentation.core.ui.widget.button.icon.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.core.ButtonColorSet

@Composable
fun iconButtonDefaultColorSet(): ButtonColorSet = ButtonColorSet(
    borderColorDisabled = Color.Unspecified,
    borderColorPressed = Color.Unspecified,
    borderColorDefault = Color.Transparent,
    foregroundColorDisabled = Theme.color.icon.disabled,
    foregroundColorPressed = Theme.color.surface.variant,
    foregroundColorDefault = Theme.color.icon.primary,
    backgroundColorDisabled = Color.Transparent,
    backgroundColorPressed = Color.Transparent,
    backgroundColorDefault = Color.Transparent
)
