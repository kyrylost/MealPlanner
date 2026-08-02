package dev.stukalo.mealplanner.presentation.core.styling.color

import androidx.compose.ui.graphics.Color

enum class ThemeColorPalette {
    ORANGE,
    GREEN
}

fun ThemeColorPalette.toPrimaryColor() = when (this) {
    ThemeColorPalette.ORANGE -> Color(0xfff17152)
    ThemeColorPalette.GREEN -> Color(0xFF34FFA1)
}
