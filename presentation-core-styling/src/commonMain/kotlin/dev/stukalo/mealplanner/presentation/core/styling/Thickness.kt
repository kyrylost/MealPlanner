package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
data class Thickness(
    val thickness1: Dp,
    val thickness2: Dp,
    val thickness4: Dp,
)

val LocalThickness = staticCompositionLocalOf {
    Thickness(
        thickness1 = Dp.Unspecified,
        thickness2 = Dp.Unspecified,
        thickness4 = Dp.Unspecified,
    )
}
