package dev.stukalo.mealplanner.presentation.core.styling.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val backgroundSecondary: Color,
    val surfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textOnPrimary: Color,
    val textOnPrimaryVariant: Color,
    val iconPrimary: Color,
    val iconOnPrimary: Color,
    val iconDisabled: Color,
    val error: Color,
    val warning: Color,
    val success: Color
)
