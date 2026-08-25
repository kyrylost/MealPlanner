package dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

internal sealed interface ViewEvent : MviSingleEvent {
    data object NavigateBack : ViewEvent
    data object SuccessAdded : ViewEvent
}
