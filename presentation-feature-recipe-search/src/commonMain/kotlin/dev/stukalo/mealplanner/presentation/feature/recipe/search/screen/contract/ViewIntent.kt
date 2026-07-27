package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data object InitialLoad : ViewIntent
    data class ApplyFilters(val filters: FilterDomainModel) : ViewIntent
    data class OnRecipeClick(val recipeId: String) : ViewIntent
    data object OnFiltersClick : ViewIntent
}
