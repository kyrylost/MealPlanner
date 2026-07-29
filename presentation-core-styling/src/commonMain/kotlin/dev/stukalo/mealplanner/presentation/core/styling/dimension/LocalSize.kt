package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

val LocalSize = staticCompositionLocalOf {
    Size(
        compactScreenWidth = Dp.Unspecified,
    )
}
