package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

internal sealed interface ViewEvent : MviSingleEvent {
    data class ApplyFilters(val filters: FilterDomainModel) : ViewEvent

    data object NavigateBack : ViewEvent
}
