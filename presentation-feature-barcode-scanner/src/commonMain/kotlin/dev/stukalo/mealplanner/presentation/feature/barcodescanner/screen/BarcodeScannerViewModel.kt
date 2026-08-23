package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.barcode_scanner_not_found
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
    val clock: Clock
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    private val lastScannedBarcodes = mutableMapOf<String, Long>()
    private var isProcessing = false

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnBarcodeScanned -> {
                if (shouldThrottle(intent.barcode) || isProcessing) return
                updateState { PartialStateChange.BarcodeChange(intent.barcode).reduce(it) }
                getProductByBarcode(intent.barcode)
            }
            is ViewIntent.OnBarcodeChange -> {
                updateState { PartialStateChange.BarcodeChange(intent.barcode).reduce(it) }
                updateState { PartialStateChange.Error(null).reduce(it) }
            }
            ViewIntent.OnScanClick -> {
                if (isProcessing) return
                getProductByBarcode(viewState.value.barcode)
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
            ViewIntent.OnManualEntryClick -> {
                updateState { PartialStateChange.ManualEntryVisibility(true).reduce(it) }
                updateState { PartialStateChange.Error(null).reduce(it) }
            }
            ViewIntent.OnDismissManualEntry -> {
                updateState { PartialStateChange.ManualEntryVisibility(false).reduce(it) }
                updateState { PartialStateChange.Error(null).reduce(it) }
            }
        }
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

        viewModelScope.launch {
            updateState { PartialStateChange.Loading(true).reduce(it) }
            updateState { PartialStateChange.Error(null).reduce(it) }
            val result = getProductByBarcodeUseCase(barcode)

            updateState { PartialStateChange.Loading(false).reduce(it) }
            isProcessing = false

            result.onSuccess { product ->
                if (product != null) {
                    updateState { PartialStateChange.ManualEntryVisibility(false).reduce(it) }
                    updateState { PartialStateChange.Navigating(true).reduce(it) }
                    sendEvent(ViewEvent.NavigateToProductDetails(barcode))
                    // Reset navigation state after some time to allow scanning again if returned
                    launch {
                        delay(RESET_NAVIGATION_DELAY_MS.milliseconds)
                        updateState { PartialStateChange.Navigating(false).reduce(it) }
                    }
                } else {
                    updateState { PartialStateChange.Error(Res.string.barcode_scanner_not_found).reduce(it) }
                    sendEvent(ViewEvent.ShowError(Res.string.barcode_scanner_not_found))
                }
            }.onFailure {
                updateState { PartialStateChange.Error(Res.string.barcode_scanner_not_found).reduce(it) }
                sendEvent(ViewEvent.ShowError(Res.string.barcode_scanner_not_found))
            }
        }
    }

    companion object {
        private const val SCAN_COOLDOWN_MS = 2000L
        private const val RESET_NAVIGATION_DELAY_MS = 1000L
    }
}
