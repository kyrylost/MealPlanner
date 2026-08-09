package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

/**
 * Helper object for convenient access to [LocalBottomBarHeight].
 */
object BottomBarHeight {
    /**
     * Current total height of the bottom navigation bar.
     * Includes the content height, internal design paddings, and system insets.
     */
    val current: Dp
        @Composable
        get() = LocalBottomBarHeight.current
}
