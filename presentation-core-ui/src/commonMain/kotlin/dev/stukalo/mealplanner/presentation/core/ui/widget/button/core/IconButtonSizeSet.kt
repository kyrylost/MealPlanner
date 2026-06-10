package dev.stukalo.mealplanner.presentation.core.ui.widget.button.core

import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.IconButtonSize

/**
 * Interface representing a set of predefined button sizes.
 *
 * This interface defines functions to obtain different button size configurations.
 *
 * References:
 *
 * - https://proandroiddev.com/compose-a-compose-button-by-composing-composable-functions-9f275772bd23
 * - https://github.com/aoriani/ComposeButton/tree/main
 */
interface IconButtonSizeSet {
    /**
     * Returns the button size configuration for a button with a size of 32dp.
     *
     * @return A [dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.IconButtonSize] instance representing the button size configuration for 32dp.
     */
    fun small(): IconButtonSize

    /**
     * Returns the button size configuration for a button with a size of 40dp.
     *
     * @return A [IconButtonSize] instance representing the button size configuration for 40dp.
     */
    fun default(): IconButtonSize
}
