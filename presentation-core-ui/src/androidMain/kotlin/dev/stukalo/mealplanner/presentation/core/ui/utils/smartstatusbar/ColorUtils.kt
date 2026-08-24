package dev.stukalo.mealplanner.presentation.core.ui.utils.smartstatusbar

import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

/**
 * Determines whether the given color is dark based on its luminance.
 *
 * @param darkColorBound takes value from 0 to 255. It specifies how dark color should be to be determined as dark.
 * @return true if the color is considered dark, false otherwise.
 */
fun Int.isDarkColor(darkColorBound: Int): Boolean {
    val luminance = (red * 77 + green * 150 + blue * 29) shr 8
    return luminance <= darkColorBound
}
