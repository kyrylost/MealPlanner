package dev.stukalo.mealplanner.presentation.feature.home.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

internal sealed interface ViewIntent : MviIntent {
    data object InitialLoad : ViewIntent
    data class OnRecipeClick(val recipeId: String) : ViewIntent
    data class OnAddNutrient(val type: NutrientType, val amount: Float) : ViewIntent
}

internal enum class NutrientType {
    PROTEINS, FATS, CARBS
}
