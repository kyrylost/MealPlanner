package dev.stukalo.mealplanner.presentation.core.styling.color.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * A data class representing the surface colors of the application.
 *
 * These colors change based on the theme (light/dark).
 */
@Immutable
data class SurfaceColors(val variant: Color)
