package dev.stukalo.mealplanner.presentation.feature.settings.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

sealed interface ViewEvent : MviSingleEvent {
    data object NavigateBack : ViewEvent
}
