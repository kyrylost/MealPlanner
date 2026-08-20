package dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

/**
 * User intents for the Onboarding screen.
 */
internal sealed interface ViewIntent : MviIntent {
    data object OnSkipClick : ViewIntent
    data object OnNextClick : ViewIntent
    data class OnSlideChange(val index: Int) : ViewIntent
}
