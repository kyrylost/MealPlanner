package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.error_unknown
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.usecase.products.GetAutoCompleteHintsUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetProductsByQueryUseCase
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * ViewModel for the product search screen.
 * Handles search queries, auto-complete suggestions, and product logging.
 *
 * @param getProductsByQueryUseCase Use case for searching products by query.
 * @param getAutoCompleteHintsUseCase Use case for fetching auto-complete hints.
 * @param logProductConsumedUseCase Use case for logging consumed products.
 */
@OptIn(FlowPreview::class)
internal class ProductSearchViewModel(
    private val getProductsByQueryUseCase: GetProductsByQueryUseCase,
    private val getAutoCompleteHintsUseCase: GetAutoCompleteHintsUseCase,
    private val logProductConsumedUseCase: LogProductConsumedUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    private val queryFlow = MutableStateFlow("")

    init {
        queryFlow
            .debounce(DEBOUNCE_MILLIS.milliseconds)
            .distinctUntilChanged()
            .filter { it.isNotBlank() && it.length >= MIN_QUERY_LENGTH }
            .onEach { query ->
                loadSuggestions(query)
            }.launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
            is ViewIntent.OnQueryChange -> {
                updateState { PartialStateChange.QueryChange(intent.query).reduce(it) }
                queryFlow.value = intent.query
            }
            ViewIntent.OnSearchClick -> {
                searchProducts(viewState.value.query)
            }
            is ViewIntent.OnSuggestionClick -> {
                updateState { PartialStateChange.QueryChange(intent.suggestion).reduce(it) }
                searchProducts(intent.suggestion)
            }
            is ViewIntent.OnProductClick -> {
                sendEvent(ViewEvent.NavigateToProductDetails(intent.product.id.orEmpty()))
            }
            is ViewIntent.OnLogProduct -> {
                logProduct(intent.product, intent.weight)
            }
            ViewIntent.OnBarcodeScannerClick -> {
                sendEvent(ViewEvent.NavigateToBarcodeScanner)
            }
        }
    }

    private fun loadSuggestions(query: String) {
        viewModelScope.launch {
            getAutoCompleteHintsUseCase(query).onSuccess { suggestions ->
                updateState { PartialStateChange.SuggestionsLoaded(suggestions).reduce(it) }
            }
        }
    }

    private fun searchProducts(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            val flow = getProductsByQueryUseCase(query)
            updateState { PartialStateChange.ProductsLoaded(flow).reduce(it) }
        }
    }

    private fun logProduct(product: ProductDomainModel, weight: Float) {
        viewModelScope.launch {
            updateState { PartialStateChange.Loading(true).reduce(it) }
            logProductConsumedUseCase(product, weight)
                .onSuccess {
                    updateState { PartialStateChange.Loading(false).reduce(it) }
                }.onFailure {
                    updateState { PartialStateChange.Loading(false).reduce(it) }
                    sendEvent(ViewEvent.ShowError(Res.string.error_unknown))
                }
        }
    }

    companion object {
        private const val DEBOUNCE_MILLIS = 1000L
        private const val MIN_QUERY_LENGTH = 3
    }
}
