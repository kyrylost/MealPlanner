package dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract

/**
 * Partial state changes for the Onboarding screen.
 */
internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    data class SlideChange(val index: Int) : PartialStateChange {
        override fun reduce(oldState: ViewState) = oldState.copy(currentSlideIndex = index)
    }
}
