package dev.stukalo.mealplanner.presentation.feature.productdetails.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewIntent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

/**
 * The product details screen logic holder.
 *
 * @param productId The product ID to load.
 * @param barcode The barcode to load.
 * @param onBackClick The callback for navigating back.
 */
@Composable
internal fun ProductDetailsScreen(productId: String?, barcode: String?, onBackClick: () -> Unit) {
    val viewModel: ProductDetailsViewModel = koinViewModel()

    LaunchedEffect(productId, barcode) {
        viewModel.onIntent(
            ViewIntent.InitialLoad(
                productId,
                barcode
            )
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
                is ViewEvent.ShowError -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
                ViewEvent.SuccessAdded -> onBackClick()
            }
        }
    ) { state ->
        ProductDetailsContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onIntent = viewModel::onIntent
        )
    }
}
