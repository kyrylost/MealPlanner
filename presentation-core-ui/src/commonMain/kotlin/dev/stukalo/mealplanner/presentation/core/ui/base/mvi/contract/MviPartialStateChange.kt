package dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract

/**
 * Interface for all partial state changes in MVI.
 * Each change is responsible for reducing the current state to a new state.
 *
 * @param S The type of the [MviViewState].
 */
interface MviPartialStateChange<S : MviViewState> {
    /**
     * Reduces the [oldState] to a new state.
     */
    fun reduce(oldState: S): S
}
