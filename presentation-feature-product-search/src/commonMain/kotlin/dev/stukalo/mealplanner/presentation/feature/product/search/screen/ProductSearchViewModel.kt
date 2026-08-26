package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.exception.ProductException
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.usecase.products.GetAutoCompleteHintsUseCase
import dev.stukalo.mealplanner.domain.usecase.products.GetProductsByQueryUseCase
import dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.product.search.core.mapper.toMessage
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
import org.jetbrains.compose.resources.StringResource
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
    private var lastSearchedQuery: String? = null

    init {
        queryFlow
            .debounce(DEBOUNCE_MILLIS.milliseconds)
            .distinctUntilChanged()
            .filter { it.trim().length >= MIN_QUERY_LENGTH }
            .onEach { query ->
                loadSuggestions(query.trim())
            }.launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
            is ViewIntent.OnQueryChange -> {
                reduce(PartialStateChange.QueryChange(intent.query))
                queryFlow.value = intent.query
            }
            ViewIntent.OnSearchClick -> {
                searchProducts(viewState.value.query)
            }
            is ViewIntent.OnSuggestionClick -> {
                reduce(PartialStateChange.QueryChange(intent.suggestion))
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
            ViewIntent.OnDismissSuggestions -> {
                reduce(PartialStateChange.DismissSuggestions)
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

    private fun loadSuggestions(query: String) {
        safeLaunch {
            getAutoCompleteHintsUseCase(query).onSuccess { suggestions ->
                reduce(PartialStateChange.SuggestionsLoaded(suggestions))
            }
        }
    }

    private fun searchProducts(query: String) {
        val trimmedQuery = query.trim()
        if (trimmedQuery.length < MIN_QUERY_LENGTH) return
        if (trimmedQuery == lastSearchedQuery) return

        lastSearchedQuery = trimmedQuery
        safeLaunch {
            val flow = getProductsByQueryUseCase(trimmedQuery)
            reduce(PartialStateChange.ProductsLoaded(flow))
        }
    }

    private fun logProduct(product: ProductDomainModel, weight: Float) {
        safeLaunch {
            reduce(PartialStateChange.Loading(true))
            logProductConsumedUseCase(product, weight)
                .onSuccess {
                    reduce(PartialStateChange.Loading(false))
                }.onFailure {
                    handleError(it)
                }
        }
    }

    companion object {
        private const val DEBOUNCE_MILLIS = 1000L
        private const val MIN_QUERY_LENGTH = 3
    }
}
