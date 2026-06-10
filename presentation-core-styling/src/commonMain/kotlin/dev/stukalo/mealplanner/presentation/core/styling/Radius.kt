package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
data class Radius(
    val radius2: Dp,
    val radius4: Dp,
    val radius8: Dp,
    val radius12: Dp,
    val radius16: Dp,
    val radius20: Dp,
    val radius24: Dp,
    val radius28: Dp,
    val radius32: Dp,
    val radius48: Dp,
    val radius64: Dp,
)

val LocalRadius = staticCompositionLocalOf {
    Radius(
        radius2 = Dp.Unspecified,
        radius4 = Dp.Unspecified,
        radius8 = Dp.Unspecified,
        radius12 = Dp.Unspecified,
        radius16 = Dp.Unspecified,
        radius20 = Dp.Unspecified,
        radius24 = Dp.Unspecified,
        radius28 = Dp.Unspecified,
        radius32 = Dp.Unspecified,
        radius48 = Dp.Unspecified,
        radius64 = Dp.Unspecified,
    )
}
