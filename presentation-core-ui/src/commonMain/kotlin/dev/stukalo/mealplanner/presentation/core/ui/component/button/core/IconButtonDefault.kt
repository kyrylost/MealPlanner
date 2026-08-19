package dev.stukalo.mealplanner.presentation.core.ui.component.button.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

/**
 * Interface representing the default configuration for a button.
 *
 * This interface defines composable functions to obtain the default color,
 * size set, animation, and shape configurations for a button.
 *
 * References:
 *
 * - https://proandroiddev.com/compose-a-compose-button-by-composing-composable-functions-9f275772bd23
 * - https://github.com/aoriani/ComposeButton/tree/main
 */
interface IconButtonDefault {
    /**
     * Returns the default [ButtonColor] configuration for the button.
     *
     * @param buttonColorSet The [ButtonColorSet] instance that provides the color set for the button.
     * @return A [ButtonColor] instance representing the default color configuration for the button.
     */
    @Composable
    fun buttonColor(buttonColorSet: ButtonColorSet): ButtonColor

    /**
     * Returns the default [dev.stukalo.mealplanner.presentation.core.ui.component.button.core.IconButtonSizeSet] configuration for the button.
     *
     * @return A [dev.stukalo.mealplanner.presentation.core.ui.component.button.core.IconButtonSizeSet] instance representing the default size configuration for the button.
     */
    @Composable
    fun buttonSizeSet(): IconButtonSizeSet

    /**
     * Returns the default [ButtonAnimation] configuration for the button.
     *
     * @return A [ButtonAnimation] instance representing the default animation configuration for the button.
     */
    @Composable
    fun animation(): ButtonAnimation

    /**
     * Returns the default [Dp] for corners of the button.
     *
     * @return A [Dp] instance representing the default corners radius for the button.
     */
    @Composable
    fun corner(): Dp
}
