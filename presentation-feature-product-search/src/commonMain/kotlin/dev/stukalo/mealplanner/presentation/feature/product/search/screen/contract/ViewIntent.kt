package dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

internal sealed interface ViewIntent : MviIntent {
    data object OnBackClick : ViewIntent

    data class OnQueryChange(val query: String) : ViewIntent

    data object OnSearchClick : ViewIntent

    data class OnSuggestionClick(val suggestion: String) : ViewIntent

    data class OnProductClick(val product: ProductDomainModel) : ViewIntent

    data class OnLogProduct(val product: ProductDomainModel, val weight: Float) : ViewIntent

    data object OnBarcodeScannerClick : ViewIntent
}
