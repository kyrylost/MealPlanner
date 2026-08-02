package dev.stukalo.mealplanner.presentation.core.navigation.transitions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

/**
 * Standard navigation transitions for the application.
 */
object NavigationTransitions {

    private const val DEFAULT_DURATION = 300

    /**
     * Enter transition for tabs. Slides from right if [isRight] is true, else from left.
     */
    fun tabEnter(isRight: Boolean): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { if (isRight) it else -it },
            animationSpec = tween(DEFAULT_DURATION)
        ) + fadeIn(animationSpec = tween(DEFAULT_DURATION))
    }

    /**
     * Exit transition for tabs. Slides to left if [isRight] is true, else to right.
     */
    fun tabExit(isRight: Boolean): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { if (isRight) -it else it },
            animationSpec = tween(DEFAULT_DURATION)
        ) + fadeOut(animationSpec = tween(DEFAULT_DURATION))
    }

    /**
     * Transition for Screen B when navigating A -> B.
     * The new screen (B) slides up from the bottom while fading in.
     * Used as `enterTransition` in NavHost.
     */
    val drillDownEnter: EnterTransition = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(DEFAULT_DURATION)
    ) + fadeIn(animationSpec = tween(DEFAULT_DURATION))

    /**
     * Transition for Screen A when navigating A -> B.
     * The current screen (A) stays in place but gradually fades out while B covers it.
     * Used as `exitTransition` in NavHost.
     */
    val drillDownExit: ExitTransition = fadeOut(animationSpec = tween(DEFAULT_DURATION))

    /**
     * Transition for Screen A when navigating B -> A (Back).
     * The original screen (A) fades back in while the top screen (B) is dismissed.
     * Used as `popEnterTransition` in NavHost.
     */
    val drillDownPopEnter: EnterTransition = fadeIn(animationSpec = tween(DEFAULT_DURATION))

    /**
     * Transition for Screen B when navigating B -> A (Back).
     * The top screen (B) slides back down to the bottom while fading out.
     * Used as `popExitTransition` in NavHost.
     */
    val drillDownPopExit: ExitTransition = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(DEFAULT_DURATION)
    ) + fadeOut(animationSpec = tween(DEFAULT_DURATION))
}
