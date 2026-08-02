package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data object InitialLoad : ViewIntent

    data class OnSearchQueryChange(val query: String) : ViewIntent

    data class ApplyFilters(val filters: FilterDomainModel?) : ViewIntent

    data object OnClearFilters : ViewIntent

    data class OnRecipeClick(val recipeId: String) : ViewIntent

    data object OnFiltersClick : ViewIntent

    data object OnBackClick : ViewIntent
}
