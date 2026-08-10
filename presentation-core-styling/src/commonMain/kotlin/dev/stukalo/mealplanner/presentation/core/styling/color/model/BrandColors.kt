package dev.stukalo.mealplanner.presentation.core.styling.color.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * A data class representing the brand colors of the application.
 *
 * These colors change based on the selected [dev.stukalo.mealplanner.presentation.core.styling.color.palette.ThemeColorPalette].
 */
@Immutable
data class BrandColors(val primary: Color, val primaryVariant: Color, val secondary: Color)
