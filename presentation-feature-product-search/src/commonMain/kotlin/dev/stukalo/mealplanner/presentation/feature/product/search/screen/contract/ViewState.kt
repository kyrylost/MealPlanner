package dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

data class ViewState(
    val query: String = "",
    val isLoading: Boolean = false,
) : MviViewState
