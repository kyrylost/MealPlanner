package dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data class OnQueryChange(val query: String) : ViewIntent
    data object OnSearchClick : ViewIntent
}
