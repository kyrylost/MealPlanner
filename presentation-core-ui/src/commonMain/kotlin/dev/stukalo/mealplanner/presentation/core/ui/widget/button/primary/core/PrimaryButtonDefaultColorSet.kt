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
        foregroundColorDisabled = Theme.color.textLight,
        foregroundColorPressed = Theme.color.textLight,
        foregroundColorDefault = Theme.color.textLight,
        backgroundColorDisabled = Theme.color.lightGray,
        backgroundColorPressed = Theme.color.orangeDark,
        backgroundColorDefault = Theme.color.orange,
    )
