package dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import org.jetbrains.compose.resources.StringResource

sealed interface ViewEvent : MviSingleEvent {
    data object NavigateBack : ViewEvent

    data object NavigateToBarcodeScanner : ViewEvent

    data class NavigateToProductDetails(val productId: String) : ViewEvent

    data class ShowError(val message: StringResource) : ViewEvent
}
