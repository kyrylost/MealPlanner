package dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange
import kotlinx.coroutines.flow.Flow

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    data class QueryChange(val query: String) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            query = query,
            suggestions = if (query.isBlank()) emptyList() else oldState.suggestions
        )
    }

    data class SuggestionsLoaded(val suggestions: List<String>) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(suggestions = suggestions)
    }

    data class ProductsLoaded(val productsFlow: Flow<PagingData<ProductDomainModel>>) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            productsFlow = productsFlow,
            suggestions = emptyList()
        )
    }

    data class Loading(val isLoading: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isLoading = isLoading)
    }
}
