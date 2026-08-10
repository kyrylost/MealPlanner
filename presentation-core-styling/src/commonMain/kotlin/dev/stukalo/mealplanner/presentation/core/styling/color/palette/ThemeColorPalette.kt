package dev.stukalo.mealplanner.presentation.core.styling.color.palette

import dev.stukalo.mealplanner.presentation.core.styling.color.token.ColorTokens

/**
 * Supported color palettes for the application theme.
 */
enum class ThemeColorPalette {
    ORANGE,
    GREEN,
    LIME,
    PINK
}

/**
 * Returns the primary color associated with the palette.
 *
 * @return The primary color for this palette from [ColorTokens].
 */
fun ThemeColorPalette.toPrimaryColor() = when (this) {
    ThemeColorPalette.ORANGE -> ColorTokens.Orange500
    ThemeColorPalette.GREEN -> ColorTokens.Green400
    ThemeColorPalette.LIME -> ColorTokens.Lime500
    ThemeColorPalette.PINK -> ColorTokens.Pink500
}
