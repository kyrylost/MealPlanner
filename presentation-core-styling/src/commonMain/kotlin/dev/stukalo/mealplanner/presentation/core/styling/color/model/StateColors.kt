package dev.stukalo.mealplanner.presentation.core.styling.color.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * A data class representing the state colors (error, success, etc.) of the application.
 *
 * These colors remain constant (static) across themes and palettes.
 */
@Immutable
data class StateColors(
    val fixedLight: Color,
    val fixedDark: Color,
    val error: Color,
    val warning: Color,
    val success: Color
)
