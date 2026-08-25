package dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    data class ProductLoaded(val product: ProductDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            product = product,
            isLoading = false
        )
    }

    data class WeightChange(val weight: String) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(weightInput = weight)
    }

    data class DialogVisibility(val isVisible: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isDialogVisible = isVisible)
    }

    data class Loading(val isLoading: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isLoading = isLoading)
    }
}
