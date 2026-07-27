package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

data class ViewState(
    val recipe: RecipeDomainModel? = null,
    val isLoading: Boolean = false,
) : MviViewState
