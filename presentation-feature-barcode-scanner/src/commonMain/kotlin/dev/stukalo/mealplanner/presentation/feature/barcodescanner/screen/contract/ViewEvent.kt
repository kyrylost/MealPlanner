package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

sealed interface ViewEvent : MviSingleEvent {
    data object NavigateBack : ViewEvent
    data class NavigateToProductDetails(val barcode: String) : ViewEvent
    data class ShowError(val message: String) : ViewEvent
}
