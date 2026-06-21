package dev.stukalo.mealplanner.presentation.core.styling.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable

@Immutable
data class Shape(
    val largeRoundedCornerShape: RoundedCornerShape,
    val normalRoundedCornerShape: RoundedCornerShape
)
