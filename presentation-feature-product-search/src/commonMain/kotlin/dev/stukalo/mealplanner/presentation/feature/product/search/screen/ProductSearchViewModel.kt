package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import dev.stukalo.mealplanner.domain.usecase.products.GetProductsByQueryUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewState

class ProductSearchViewModel(
    private val getProductsByQueryUseCase: GetProductsByQueryUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnQueryChange -> {
                updateState { it.copy(query = intent.query) }
            }
            ViewIntent.OnSearchClick -> {
                // Perform search
            }
        }
    }
}
