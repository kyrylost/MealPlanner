package dev.stukalo.mealplanner.presentation.core.ui.component.button.core

import kotlin.jvm.JvmStatic

/**
 * Object representing different interaction states for a button.
 *
 * This object contains constants that define various states such as hover, pressed, and focused.
 *
 * References:
 *
 * - https://proandroiddev.com/compose-a-compose-button-by-composing-composable-functions-9f275772bd23
 * - https://github.com/aoriani/ComposeButton/tree/main
 */
object ButtonInteractionState {
    /**
     * Represents the hover state of a button.
     */
    @JvmStatic
    val HOVER = 1.shl(0)

    /**
     * Represents the pressed state of a button.
     */
    @JvmStatic
    val PRESSED = 1.shl(1)

    /**
     * Represents the focused state of a button.
     */
    @JvmStatic
    val FOCUSED = 1.shl(2)
}
