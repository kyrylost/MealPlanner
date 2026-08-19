package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    data class RecipeLoaded(val recipe: RecipeDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            recipe = recipe,
            isLoading = false
        )
    }

    data class Loading(val isLoading: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isLoading = isLoading)
    }
}
