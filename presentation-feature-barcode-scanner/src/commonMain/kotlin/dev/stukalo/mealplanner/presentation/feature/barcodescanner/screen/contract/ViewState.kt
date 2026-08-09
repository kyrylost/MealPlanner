package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

data class ViewState(
    val barcode: String = "",
    val isLoading: Boolean = false,
    val isManualEntryVisible: Boolean = false,
    val error: String? = null,
    val isNavigating: Boolean = false
) : MviViewState
