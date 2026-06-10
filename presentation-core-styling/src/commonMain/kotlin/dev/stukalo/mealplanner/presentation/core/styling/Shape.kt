package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

@Immutable
data class Shape(
    val largeRoundedCornerShape: RoundedCornerShape,
    val normalRoundedCornerShape: RoundedCornerShape
)

val LocalShape = staticCompositionLocalOf {
    Shape(
        largeRoundedCornerShape = RoundedCornerShape(Dp.Unspecified),
        normalRoundedCornerShape = RoundedCornerShape(Dp.Unspecified)
    )
}