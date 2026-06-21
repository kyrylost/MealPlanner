package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

object BottomBarHeight {
    val current: Dp
        @Composable
        get() = LocalBottomBarHeight.current
}
