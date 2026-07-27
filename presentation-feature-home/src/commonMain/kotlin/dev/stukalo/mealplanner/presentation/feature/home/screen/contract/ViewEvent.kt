package dev.stukalo.mealplanner.presentation.feature.home.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

internal sealed interface ViewEvent : MviSingleEvent {
    data class NavigateToRecipeDetails(val recipeId: String) : ViewEvent
    data object NavigateToRecipeSearch : ViewEvent
}
