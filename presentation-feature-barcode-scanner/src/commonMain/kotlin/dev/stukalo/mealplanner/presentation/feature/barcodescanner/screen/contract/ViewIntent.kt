package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

internal sealed interface ViewIntent : MviIntent {
    data class OnBarcodeScanned(val barcode: String) : ViewIntent
    data class OnBarcodeChange(val barcode: String) : ViewIntent
    data object OnScanClick : ViewIntent
    data object OnBackClick : ViewIntent
    data object OnManualEntryClick : ViewIntent
    data object OnDismissManualEntry : ViewIntent
}
