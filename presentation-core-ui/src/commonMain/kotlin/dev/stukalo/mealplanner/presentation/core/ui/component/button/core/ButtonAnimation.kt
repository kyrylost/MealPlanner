package dev.stukalo.mealplanner.presentation.core.ui.component.button.core

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable

/**
 * Immutable interface representing the configuration for button animations.
 *
 * This interface defines properties for animation duration and easing, ensuring that implementations are immutable.
 *
 * References:
 *
 * - https://proandroiddev.com/compose-a-compose-button-by-composing-composable-functions-9f275772bd23
 * - https://github.com/aoriani/ComposeButton/tree/main
 */
@Immutable
interface ButtonAnimation {
    /**
     * The duration of the animation in milliseconds.
     */
    val duration: Int

    /**
     * The easing function used for the animation.
     */
    val easing: Easing
}
