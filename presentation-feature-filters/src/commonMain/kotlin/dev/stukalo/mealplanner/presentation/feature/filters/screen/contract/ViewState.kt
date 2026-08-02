package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

data class ViewState(val filters: FilterDomainModel = FilterDomainModel(), val isLoading: Boolean = false) :
    MviViewState
