package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

/**
 * Represents the immutable state of the Recipe Search screen.
 *
 * @property searchQuery The current text in the search bar.
 * @property filters The currently applied recipe filters.
 */
internal data class ViewState(val searchQuery: String = "", val filters: FilterDomainModel? = null) : MviViewState
