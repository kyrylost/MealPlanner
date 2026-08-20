package dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

/**
 * One-time events for the Onboarding screen.
 */
internal sealed interface ViewEvent : MviSingleEvent {
    data object NavigateToWelcome : ViewEvent
}
