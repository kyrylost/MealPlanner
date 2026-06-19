package dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    data class Loading(val isLoading: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isLoading = isLoading)
    }
}
