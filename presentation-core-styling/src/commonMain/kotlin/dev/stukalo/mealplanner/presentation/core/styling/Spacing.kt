package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
data class Spacing(
    val space2: Dp,
    val space4: Dp,
    val space8: Dp,
    val space12: Dp,
    val space16: Dp,
    val space20: Dp,
    val space24: Dp,
    val space28: Dp,
    val space32: Dp,
    val space48: Dp,
    val space64: Dp,
)

val LocalSpacing = staticCompositionLocalOf {
    Spacing(
        space2 = Dp.Unspecified,
        space4 = Dp.Unspecified,
        space8 = Dp.Unspecified,
        space12 = Dp.Unspecified,
        space16 = Dp.Unspecified,
        space20 = Dp.Unspecified,
        space24 = Dp.Unspecified,
        space28 = Dp.Unspecified,
        space32 = Dp.Unspecified,
        space48 = Dp.Unspecified,
        space64 = Dp.Unspecified,
    )
}
