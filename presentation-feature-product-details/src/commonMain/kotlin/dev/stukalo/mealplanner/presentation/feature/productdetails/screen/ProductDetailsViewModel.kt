package dev.stukalo.mealplanner.presentation.feature.productdetails.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.usecase.products.GetProductDetailsUseCase
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewState
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productId: String?,
    private val barcode: String?,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val logProductConsumedUseCase: LogProductConsumedUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    init {
        onIntent(ViewIntent.InitialLoad)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.InitialLoad -> {
                loadProduct()
            }
            is ViewIntent.OnWeightChange -> {
                updateState { it.copy(weightInput = intent.weight) }
            }
            ViewIntent.OnAddConsumedClick -> {
                updateState { it.copy(isDialogVisible = true) }
            }
            is ViewIntent.OnConfirmLog -> {
                updateState { it.copy(isDialogVisible = false) }
                logConsumed(intent.weight)
            }
            ViewIntent.OnDismissDialog -> {
                updateState { it.copy(isDialogVisible = false) }
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }

    private fun loadProduct() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            val product = getProductDetailsUseCase(productId, barcode)
            if (product != null) {
                updateState { it.copy(isLoading = false, product = product, error = null) }
            } else {
                updateState { it.copy(isLoading = false, error = "PRODUCT_NOT_FOUND") }
            }
        }
    }

    private fun logConsumed(weight: Float) {
        val product = viewState.value.product ?: return
        if (weight <= 0) return

        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            logProductConsumedUseCase(product, weight)
                .onSuccess {
                    updateState { it.copy(isLoading = false) }
                    sendEvent(ViewEvent.SuccessAdded)
                }.onFailure {
                    updateState { it.copy(isLoading = false) }
                    sendEvent(ViewEvent.ShowError(it.message ?: "Unknown error"))
                }
        }
    }
}
