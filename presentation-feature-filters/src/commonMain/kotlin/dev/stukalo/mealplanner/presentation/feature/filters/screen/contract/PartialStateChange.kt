package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    data class FiltersChanged(val filters: FilterDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(filters = filters)
    }
}
