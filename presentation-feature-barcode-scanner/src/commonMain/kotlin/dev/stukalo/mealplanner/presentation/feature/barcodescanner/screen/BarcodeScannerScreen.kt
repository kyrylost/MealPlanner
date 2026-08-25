package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

/**
 * The barcode scanner screen logic holder.
 *
 * @param onBackClick The callback for navigating back.
 * @param onNavigateToProductDetails The callback for navigating to product details.
 */
@Composable
internal fun BarcodeScannerScreen(onBackClick: () -> Unit, onNavigateToProductDetails: (String) -> Unit) {
    val viewModel: BarcodeScannerViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    MviScreen(
        viewModel = viewModel,
        snackbarHostState = snackbarHostState,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
                is ViewEvent.NavigateToProductDetails -> onNavigateToProductDetails(event.barcode)
            }
        }
    ) { state ->
        BarcodeScannerContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onIntent = viewModel::onIntent
        )
    }
}
