package dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract

/**
 * A wrapper for all side effects (single events) in the MVI architecture.
 * It separates feature-specific events from system-wide events (like snackbars).
 *
 * @param E The type of feature-specific [MviSingleEvent].
 */
sealed interface MviSideEffect<out E : MviSingleEvent> {

    /**
     * Represents a feature-specific event that should be handled by the screen.
     */
    data class Feature<out E : MviSingleEvent>(val event: E) : MviSideEffect<E>

    /**
     * Represents a system-wide event (e.g., ShowSnackbar) that can be handled automatically.
     */
    data class System(val event: MviSingleEvent) : MviSideEffect<Nothing>
}
