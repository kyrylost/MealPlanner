package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.barcode_scanner_not_found
import dev.stukalo.mealplanner.domain.usecase.products.GetProductByBarcodeUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds

/**
 * The ViewModel for the barcode scanner screen.
 * Handles barcode scanning logic and manual entry.
 *
 * @param getProductByBarcodeUseCase The use case to fetch product details by barcode.
 */
class BarcodeScannerViewModel(private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase, val clock: Clock) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    private val lastScannedBarcodes = mutableMapOf<String, Long>()
    private var isProcessing = false

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnBarcodeScanned -> {
                if (shouldThrottle(intent.barcode) || isProcessing) return
                updateState { it.copy(barcode = intent.barcode) }
                getProductByBarcode(intent.barcode)
            }
            is ViewIntent.OnBarcodeChange -> {
                updateState { it.copy(barcode = intent.barcode, error = null) }
            }
            ViewIntent.OnScanClick -> {
                if (isProcessing) return
                getProductByBarcode(viewState.value.barcode)
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
            ViewIntent.OnManualEntryClick -> {
                updateState { it.copy(isManualEntryVisible = true, error = null) }
            }
            ViewIntent.OnDismissManualEntry -> {
                updateState { it.copy(isManualEntryVisible = false, error = null) }
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
            updateState { it.copy(isLoading = true, error = null) }
            val result = getProductByBarcodeUseCase(barcode)

            updateState { it.copy(isLoading = false) }
            isProcessing = false

            result.onSuccess { product ->
                if (product != null) {
                    updateState { it.copy(isManualEntryVisible = false, isNavigating = true) }
                    sendEvent(ViewEvent.NavigateToProductDetails(barcode))
                    // Reset navigation state after some time to allow scanning again if returned
                    launch {
                        delay(RESET_NAVIGATION_DELAY_MS.milliseconds)
                        updateState { it.copy(isNavigating = false) }
                    }
                } else {
                    val errorMessage = getString(Res.string.barcode_scanner_not_found)
                    updateState { it.copy(error = errorMessage) }
                    sendEvent(ViewEvent.ShowError(errorMessage))
                }
            }.onFailure {
                val errorMessage = getString(Res.string.barcode_scanner_not_found)
                updateState { it.copy(error = errorMessage) }
                sendEvent(ViewEvent.ShowError(errorMessage))
            }
        }
    }

    companion object {
        private const val SCAN_COOLDOWN_MS = 2000L
        private const val RESET_NAVIGATION_DELAY_MS = 1000L
    }
}
