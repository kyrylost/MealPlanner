package dev.stukalo.mealplanner.presentation.core.styling.color.factory

import dev.stukalo.mealplanner.presentation.core.styling.color.model.AppColors
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.ThemeColorPalette
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.green.ColorGreenDark
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.green.ColorGreenLight
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.orange.ColorOrangeDark
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.orange.ColorOrangeLight

/**
 * Factory for creating [dev.stukalo.mealplanner.presentation.core.styling.color.model.AppColors] based on the selected palette and theme mode.
 */
internal object AppColorsFactory {
    /**
     * Creates an [dev.stukalo.mealplanner.presentation.core.styling.color.model.AppColors] instance.
     *
     * @param palette The selected [ThemeColorPalette].
     * @param isDark Whether the theme is in dark mode.
     * @return The corresponding [dev.stukalo.mealplanner.presentation.core.styling.color.model.AppColors].
     */
    fun create(palette: ThemeColorPalette, isDark: Boolean): AppColors = when (palette) {
        ThemeColorPalette.ORANGE -> if (isDark) ColorOrangeDark else ColorOrangeLight
        ThemeColorPalette.GREEN -> if (isDark) ColorGreenDark else ColorGreenLight
    }
}
