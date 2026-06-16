package dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonColorSet
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun primaryButtonDefaultColorSet(): ButtonColorSet =
    ButtonColorSet(
        borderColorDisabled = Color.Unspecified,
        borderColorPressed = Color.Unspecified,
        borderColorDefault = Color.Transparent,
        foregroundColorDisabled = Theme.color.textOnPrimary,
        foregroundColorPressed = Theme.color.textOnPrimary,
        foregroundColorDefault = Theme.color.textOnPrimary,
        backgroundColorDisabled = Theme.color.surfaceVariant,
        backgroundColorPressed = Theme.color.primaryVariant,
        backgroundColorDefault = Theme.color.primary,
    )
