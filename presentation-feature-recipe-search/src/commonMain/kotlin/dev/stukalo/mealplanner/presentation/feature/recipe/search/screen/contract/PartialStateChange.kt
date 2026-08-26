package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

/**
 * Encapsulates atomic updates to the [ViewState].
 */
internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    /**
     * Updates the search query in the state.
     *
     * @property query The new search query.
     */
    data class SearchQueryChange(val query: String) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(searchQuery = query)
    }

    /**
     * Updates the filters in the state.
     *
     * @property filters The new filters.
     */
    data class FiltersChanged(val filters: FilterDomainModel?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(filters = filters)
    }
}
