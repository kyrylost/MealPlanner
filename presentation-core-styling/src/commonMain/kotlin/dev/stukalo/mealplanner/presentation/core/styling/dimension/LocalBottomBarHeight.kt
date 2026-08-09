package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

/**
 * CompositionLocal that provides the total height of the bottom navigation bar.
 *
 * The value is dynamically calculated and includes:
 * - The height of the bar content.
 * - Internal paddings of the bar relative to the screen edges (Theme.spacing.space16).
 * - System insets for the navigation bars.
 *
 * This value is used to add padding to screen content to ensure correct rendering
 * of elements above the "floating" navigation bar.
 */
val LocalBottomBarHeight = compositionLocalOf { 0.dp }
