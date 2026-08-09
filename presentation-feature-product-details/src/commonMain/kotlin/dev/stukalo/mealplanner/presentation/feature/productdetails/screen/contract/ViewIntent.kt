package dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data object InitialLoad : ViewIntent
    data class OnWeightChange(val weight: String) : ViewIntent
    data object OnAddConsumedClick : ViewIntent
    data class OnConfirmLog(val weight: Float) : ViewIntent
    data object OnDismissDialog : ViewIntent
    data object OnBackClick : ViewIntent
}
