package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalBottomBarHeight = compositionLocalOf { 0.dp }

object BottomBarHeight {
    val current: Dp
        @Composable
        get() = LocalBottomBarHeight.current
}
