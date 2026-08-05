package dev.stukalo.mealplanner.presentation.core.ui.utils.smartstatusbar

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private const val DEFAULT_REFRESH_TIME = 500L
private const val DEFAULT_RECHECK_NUMBER = 6

/**
 * Defines the strategy for refreshing the status bar icon color analysis.
 */
sealed interface RefreshPolicy {
    /**
     * Performs the analysis only once after a specified delay.
     * Use this for static screens where the background doesn't change after loading.
     *
     * @param waitBeforeCheck Time to wait before performing the single check.
     */
    data class OneTimeCheck(val waitBeforeCheck: Duration = DEFAULT_REFRESH_TIME.milliseconds) : RefreshPolicy

    /**
     * Refreshes the status bar icon color whenever a user interaction occurs.
     * This is the recommended policy for apps with scrolling content or transitions.
     *
     * **Note:** This policy requires calling [notifyAboutInteraction] from your Activity's
     * `dispatchTouchEvent` or a similar global interaction hook.
     *
     * @param debounce The minimum time between two consecutive interaction-triggered checks.
     * @param recheck Number of additional checks to perform after the initial interaction.
     * Useful for capturing the end state of long-running animations or transitions.
     * @param waitAfterCheck Time to wait between rechecks.
     */
    data class RefreshOnInteraction(
        val debounce: Duration = DEFAULT_REFRESH_TIME.milliseconds,
        val recheck: Int = DEFAULT_RECHECK_NUMBER,
        val waitAfterCheck: Duration = DEFAULT_REFRESH_TIME.milliseconds
    ) : RefreshPolicy

    /**
     * Periodically refreshes the status bar icon color.
     * Use this with caution as it can be resource-intensive.
     *
     * @param waitAfterCheck The interval between consecutive checks.
     */
    data class RefreshContinuously(val waitAfterCheck: Duration = DEFAULT_REFRESH_TIME.milliseconds) : RefreshPolicy
}
