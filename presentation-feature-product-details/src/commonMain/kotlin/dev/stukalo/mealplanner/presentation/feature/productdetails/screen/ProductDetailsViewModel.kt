package dev.stukalo.mealplanner.presentation.feature.productdetails.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.usecase.products.GetProductDetailsUseCase
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewState
import kotlinx.coroutines.launch

internal class ProductDetailsViewModel(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val logProductConsumedUseCase: LogProductConsumedUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.InitialLoad -> {
                loadProduct(intent.productId, intent.barcode)
            }
            is ViewIntent.OnWeightChange -> {
                updateState { PartialStateChange.WeightChange(intent.weight).reduce(it) }
            }
            ViewIntent.OnAddConsumedClick -> {
                updateState { PartialStateChange.DialogVisibility(true).reduce(it) }
            }
            is ViewIntent.OnConfirmLog -> {
                updateState { PartialStateChange.DialogVisibility(false).reduce(it) }
                logConsumed(intent.weight)
            }
            ViewIntent.OnDismissDialog -> {
                updateState { PartialStateChange.DialogVisibility(false).reduce(it) }
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }

    private fun loadProduct(productId: String?, barcode: String?) {
        viewModelScope.launch {
            updateState { PartialStateChange.Loading(true).reduce(it) }
            val product = getProductDetailsUseCase(productId, barcode)
            if (product != null) {
                updateState { PartialStateChange.ProductLoaded(product).reduce(it) }
            } else {
                updateState { PartialStateChange.Error("PRODUCT_NOT_FOUND").reduce(it) }
            }
        }
    }

    private fun logConsumed(weight: Float) {
        val product = viewState.value.product ?: return
        if (weight <= 0) return

        viewModelScope.launch {
            updateState { PartialStateChange.Loading(true).reduce(it) }
            logProductConsumedUseCase(product, weight)
                .onSuccess {
                    updateState { PartialStateChange.Loading(false).reduce(it) }
                    sendEvent(ViewEvent.SuccessAdded)
                }.onFailure {
                    updateState { PartialStateChange.Loading(false).reduce(it) }
                    sendEvent(ViewEvent.ShowError(it.message ?: "Unknown error"))
                }
        }
    }
}
