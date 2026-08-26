package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

/**
 * Represents the UI state for the Filters feature.
 *
 * @param filters The current state of filters.
 */
internal data class ViewState(val filters: FilterDomainModel = FilterDomainModel()) : MviViewState
