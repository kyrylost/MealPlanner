package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data class LoadRecipe(val id: String) : ViewIntent
    data class OnLogMealClick(val weight: Float) : ViewIntent
    data object OnBackClick : ViewIntent
}
