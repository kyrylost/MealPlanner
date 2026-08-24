package dev.stukalo.mealplanner.presentation.feature.host.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

/**
 * Intents for the Host feature.
 */
internal sealed interface ViewIntent : MviIntent {
    /**
     * Triggered when the locale changes.
     */
    data class OnLocaleChanged(val locale: String) : ViewIntent
}
