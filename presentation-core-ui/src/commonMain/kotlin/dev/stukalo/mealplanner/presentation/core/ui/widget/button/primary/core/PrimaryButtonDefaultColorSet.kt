package dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.core.ButtonColorSet

@Composable
fun primaryButtonDefaultColorSet(): ButtonColorSet = ButtonColorSet(
    borderColorDisabled = Color.Unspecified,
    borderColorPressed = Color.Unspecified,
    borderColorDefault = Color.Transparent,
    foregroundColorDisabled = Theme.color.text.onPrimary,
    foregroundColorPressed = Theme.color.text.onPrimary,
    foregroundColorDefault = Theme.color.text.onPrimary,
    backgroundColorDisabled = Theme.color.surface.variant,
    backgroundColorPressed = Theme.color.brand.primaryVariant,
    backgroundColorDefault = Theme.color.brand.primary
)
