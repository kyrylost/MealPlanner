package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen

import dev.stukalo.mealplanner.domain.model.exception.ProductException
import dev.stukalo.mealplanner.domain.usecase.products.GetProductByBarcodeUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds

/**
 * The ViewModel for the barcode scanner screen.
 * Handles barcode scanning logic and manual entry.
 *
 * @param getProductByBarcodeUseCase The use case to fetch product details by barcode.
 */
internal class BarcodeScannerViewModel(
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase,
    private val clock: Clock
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    private val lastScannedBarcodes = mutableMapOf<String, Long>()
    private var isProcessing = false

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnBarcodeScanned -> {
                if (shouldThrottle(intent.barcode) || isProcessing) return
                reduce(PartialStateChange.BarcodeChange(intent.barcode))
                getProductByBarcode(intent.barcode)
            }
            is ViewIntent.OnBarcodeChange -> {
                reduce(PartialStateChange.BarcodeChange(intent.barcode))
                reduce(PartialStateChange.Error(null))
            }
            ViewIntent.OnScanClick -> {
                if (isProcessing) return
                getProductByBarcode(viewState.value.barcode)
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
            ViewIntent.OnManualEntryClick -> {
                reduce(PartialStateChange.ManualEntryVisibility(true))
                reduce(PartialStateChange.Error(null))
            }
            ViewIntent.OnDismissManualEntry -> {
                reduce(PartialStateChange.ManualEntryVisibility(false))
                reduce(PartialStateChange.Error(null))
            }
        }
    }

    override fun handleError(throwable: Throwable) {
        isProcessing = false
        reduce(PartialStateChange.Loading(false))
        super.handleError(throwable)
    }

    private fun shouldThrottle(barcode: String): Boolean {
        val now = clock.now().toEpochMilliseconds()
        val lastTime = lastScannedBarcodes[barcode] ?: 0L
        if (now - lastTime < SCAN_COOLDOWN_MS) return true
        lastScannedBarcodes[barcode] = now
        return false
    }

    /**
     * Fetches product details by the given [barcode].
     */
    private fun getProductByBarcode(barcode: String) {
        if (barcode.isBlank() || viewState.value.isNavigating || isProcessing) return

        isProcessing = true

        safeLaunch {
            reduce(PartialStateChange.Loading(true))
            reduce(PartialStateChange.Error(null))
            val result = getProductByBarcodeUseCase(barcode)

            reduce(PartialStateChange.Loading(false))
            isProcessing = false

            result.onSuccess { product ->
                if (product != null) {
                    reduce(PartialStateChange.ManualEntryVisibility(false))
                    reduce(PartialStateChange.Navigating(true))
                    sendEvent(ViewEvent.NavigateToProductDetails(barcode))
                    // Reset navigation state after some time to allow scanning again if returned
                    launch {
                        delay(RESET_NAVIGATION_DELAY_MS.milliseconds)
                        reduce(PartialStateChange.Navigating(false))
                    }
                } else {
                    handleError(ProductException.ProductNotFound())
                }
            }.onFailure {
                handleError(it)
            }
        }
    }

    companion object {
        private const val SCAN_COOLDOWN_MS = 2000L
        private const val RESET_NAVIGATION_DELAY_MS = 1000L
    }
}
