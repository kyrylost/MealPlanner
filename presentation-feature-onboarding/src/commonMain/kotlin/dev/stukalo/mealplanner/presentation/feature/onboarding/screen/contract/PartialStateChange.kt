package dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract

/**
 * Partial state changes for the Onboarding screen.
 */
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    data class SlideChange(val index: Int) : PartialStateChange {
        override fun reduce(oldState: ViewState) = oldState.copy(currentSlideIndex = index)
    }
}
