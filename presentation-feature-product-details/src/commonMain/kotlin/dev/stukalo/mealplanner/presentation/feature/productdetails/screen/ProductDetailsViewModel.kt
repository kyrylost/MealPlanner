package dev.stukalo.mealplanner.presentation.feature.productdetails.screen

import dev.stukalo.mealplanner.domain.model.exception.ProductException
import dev.stukalo.mealplanner.domain.usecase.products.GetProductDetailsUseCase
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.productdetails.core.mapper.toMessage
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewState
import org.jetbrains.compose.resources.StringResource

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
                reduce(PartialStateChange.WeightChange(intent.weight))
            }
            ViewIntent.OnAddConsumedClick -> {
                reduce(PartialStateChange.DialogVisibility(true))
            }
            is ViewIntent.OnConfirmLog -> {
                reduce(PartialStateChange.DialogVisibility(false))
                logConsumed(intent.weight)
            }
            ViewIntent.OnDismissDialog -> {
                reduce(PartialStateChange.DialogVisibility(false))
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }

    override fun handleError(throwable: Throwable) {
        reduce(PartialStateChange.Loading(false))
        super.handleError(throwable)
    }

    override fun mapThrowable(throwable: Throwable): StringResource = when (throwable) {
        is ProductException -> throwable.toMessage()
        else -> super.mapThrowable(throwable)
    }

    private fun loadProduct(productId: String?, barcode: String?) {
        safeLaunch {
            reduce(PartialStateChange.Loading(true))
            val product = getProductDetailsUseCase(productId, barcode)
            if (product != null) {
                reduce(PartialStateChange.ProductLoaded(product))
            } else {
                handleError(ProductException.ProductNotFound())
            }
        }
    }

    private fun logConsumed(weight: Float) {
        val product = viewState.value.product ?: return
        if (weight <= 0) return

        safeLaunch {
            reduce(PartialStateChange.Loading(true))
            logProductConsumedUseCase(product, weight)
                .onSuccess {
                    reduce(PartialStateChange.Loading(false))
                    sendEvent(ViewEvent.SuccessAdded)
                }.onFailure {
                    handleError(it)
                }
        }
    }
}
