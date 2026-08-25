package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

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
