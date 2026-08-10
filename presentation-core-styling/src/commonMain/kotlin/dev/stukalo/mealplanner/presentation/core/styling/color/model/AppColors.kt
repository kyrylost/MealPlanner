package dev.stukalo.mealplanner.presentation.core.styling.color.model

import androidx.compose.runtime.Immutable

/**
 * A data class representing the grouped color palette of the application.
 *
 * This class follows the design system tokens for consistent color usage across the app.
 * It aggregates colors that are constant, theme-dependent (light/dark), and palette-dependent.
 */
@Immutable
data class AppColors(
    val brand: BrandColors,
    val text: TextColors,
    val icon: IconColors,
    val background: BackgroundColors,
    val surface: SurfaceColors,
    val state: StateColors,
    val quality: QualityColors
)
