package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

internal sealed interface ViewEvent : MviSingleEvent {
    data object NavigateToMainScreen : ViewEvent
}
