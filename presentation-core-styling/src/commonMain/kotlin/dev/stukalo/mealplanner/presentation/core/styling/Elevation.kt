package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
data class Elevation(
    val small: Dp,
    val normal: Dp,
    val large: Dp,
    val extraLarge: Dp,
)

val LocalElevation = staticCompositionLocalOf {
    Elevation(
        small = Dp.Unspecified,
        normal = Dp.Unspecified,
        large = Dp.Unspecified,
        extraLarge = Dp.Unspecified,
    )
}