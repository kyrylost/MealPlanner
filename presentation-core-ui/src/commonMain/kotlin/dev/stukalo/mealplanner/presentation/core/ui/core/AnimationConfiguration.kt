package dev.stukalo.mealplanner.presentation.core.ui.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import dev.stukalo.mealplanner.presentation.core.ui.core.AnimationConfiguration.Duration.DEFAULT

/**
 * Configuration object for animation settings.
 *
 * This object contains nested objects and constants that define various animation-related configurations,
 * such as duration.
 */
object AnimationConfiguration {
    /**
     * Object holding constants for animation durations.
     */
    object Duration {
        /**
         * The short animation duration in milliseconds.
         */
        const val SHORT = 150

        /**
         * The default animation duration in milliseconds.
         */
        const val DEFAULT = 500
    }

    /**
     * Object holding functions for animation transitions.
     */
    object Transition {
        /**
         * Returns a default transition that combines a fade-in and fade-out animation with the default duration.
         *
         * @return A lambda function defining the content transform for the transition.
         */
        fun <T> default(): AnimatedContentTransitionScope<T>.() -> ContentTransform = {
            fadeIn(
                animationSpec = tween(DEFAULT)
            ) togetherWith
                fadeOut(
                    animationSpec = tween(DEFAULT)
                )
        }
    }
}
