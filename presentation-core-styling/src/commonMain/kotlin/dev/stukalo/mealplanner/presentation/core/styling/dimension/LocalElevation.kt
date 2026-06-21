package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

val LocalElevation = staticCompositionLocalOf {
    Elevation(
        small = Dp.Unspecified,
        normal = Dp.Unspecified,
        large = Dp.Unspecified,
        extraLarge = Dp.Unspecified,
    )
}
