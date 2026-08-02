package dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
import kotlinx.coroutines.flow.Flow

data class ViewState(
    val query: String = "",
    val isLoading: Boolean = false,
    val suggestions: List<String> = emptyList(),
    val productsFlow: Flow<PagingData<ProductDomainModel>>? = null,
) : MviViewState
