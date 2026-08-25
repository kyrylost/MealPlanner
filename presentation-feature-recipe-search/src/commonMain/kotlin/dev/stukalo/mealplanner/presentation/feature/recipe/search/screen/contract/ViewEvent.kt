package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

internal sealed interface ViewEvent : MviSingleEvent {
    data class NavigateToRecipeDetails(val recipeId: String) : ViewEvent

    data object NavigateToFilters : ViewEvent

    data object NavigateBack : ViewEvent
}
