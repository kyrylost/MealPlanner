package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

/**
 * Sealed interface representing user intentions or system triggers on the Recipe Search screen.
 */
internal sealed interface ViewIntent : MviIntent {
    /**
     * Triggered when the screen is first loaded to fetch initial data.
     */
    data object InitialLoad : ViewIntent

    /**
     * Triggered when the search query text changes.
     *
     * @property query The new search query string.
     */
    data class OnSearchQueryChange(val query: String) : ViewIntent

    /**
     * Triggered when new filters are applied.
     *
     * @property filters The filters to apply.
     */
    data class ApplyFilters(val filters: FilterDomainModel?) : ViewIntent

    /**
     * Triggered when all active filters should be cleared.
     */
    data object OnClearFilters : ViewIntent

    /**
     * Triggered when a recipe item is clicked.
     *
     * @property recipeId The ID of the clicked recipe.
     */
    data class OnRecipeClick(val recipeId: String) : ViewIntent

    /**
     * Triggered when the filters button is clicked.
     */
    data object OnFiltersClick : ViewIntent

    /**
     * Triggered when the back button is clicked.
     */
    data object OnBackClick : ViewIntent
}
