package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

/**
 * Defines one-time events for the Filters feature.
 */
internal sealed interface ViewEvent : MviSingleEvent {
    /** Emitted when filters should be applied and the screen should close. */
    data class ApplyFilters(val filters: FilterDomainModel) : ViewEvent

    /** Emitted when the screen should be closed without applying changes. */
    data object NavigateBack : ViewEvent
}
