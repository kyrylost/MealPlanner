package dev.stukalo.mealplanner.presentation.core.ui.widget.button.icon.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonColorSet
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun iconButtonDefaultColorSet(): ButtonColorSet = ButtonColorSet(
    borderColorDisabled = Color.Unspecified,
    borderColorPressed = Color.Unspecified,
    borderColorDefault = Color.Transparent,
    foregroundColorDisabled = Theme.color.iconDisabled,
    foregroundColorPressed = Theme.color.surfaceVariant,
    foregroundColorDefault = Theme.color.iconPrimary,
    backgroundColorDisabled = Color.Transparent,
    backgroundColorPressed = Color.Transparent,
    backgroundColorDefault = Color.Transparent
)
