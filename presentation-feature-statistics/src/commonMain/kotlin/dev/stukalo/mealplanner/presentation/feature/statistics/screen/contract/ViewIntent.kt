package dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data object InitialLoad : ViewIntent

    data class OnMealConsumed(val slotId: Int) : ViewIntent

    data class OnMealClick(val meal: MealSlotProgress) : ViewIntent

    data object OnDismissDialog : ViewIntent
}
