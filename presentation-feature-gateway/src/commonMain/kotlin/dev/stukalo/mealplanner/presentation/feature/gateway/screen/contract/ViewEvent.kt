package dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

internal sealed interface ViewEvent : MviSingleEvent {
    data object NavigateToMain : ViewEvent

    data object NavigateToWelcome : ViewEvent
}
