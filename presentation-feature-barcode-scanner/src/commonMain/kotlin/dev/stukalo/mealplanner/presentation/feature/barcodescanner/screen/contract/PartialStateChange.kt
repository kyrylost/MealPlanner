package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    data class BarcodeChange(val barcode: String) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(barcode = barcode)
    }

    data class ManualEntryVisibility(val isVisible: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isManualEntryVisible = isVisible)
    }

    data class Loading(val isLoading: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isLoading = isLoading)
    }

    data class Navigating(val isNavigating: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isNavigating = isNavigating)
    }

    data class Error(val message: String?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(error = message, isLoading = false)
    }
}
