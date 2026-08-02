package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

val LocalRadius =
    staticCompositionLocalOf {
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
            radius64 = Dp.Unspecified
        )
    }
