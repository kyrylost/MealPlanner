package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

val LocalThickness = staticCompositionLocalOf {
    Thickness(
        thickness1 = Dp.Unspecified,
        thickness2 = Dp.Unspecified,
        thickness4 = Dp.Unspecified,
    )
}
