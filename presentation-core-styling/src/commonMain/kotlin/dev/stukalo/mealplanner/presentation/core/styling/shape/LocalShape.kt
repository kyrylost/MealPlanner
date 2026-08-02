package dev.stukalo.mealplanner.presentation.core.styling.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

val LocalShape =
    staticCompositionLocalOf {
        Shape(
            largeRoundedCornerShape = RoundedCornerShape(Dp.Unspecified),
            normalRoundedCornerShape = RoundedCornerShape(Dp.Unspecified)
        )
    }
