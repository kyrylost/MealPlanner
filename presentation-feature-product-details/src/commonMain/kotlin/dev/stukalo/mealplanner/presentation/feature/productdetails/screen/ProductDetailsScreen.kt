package dev.stukalo.mealplanner.presentation.feature.productdetails.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewIntent
import org.koin.compose.viewmodel.koinViewModel

/**
 * The product details screen logic holder.
 *
 * @param productId The product ID to load.
 * @param barcode The barcode to load.
 * @param onBackClick The callback for navigating back.
 */
@Composable
fun ProductDetailsScreen(productId: String?, barcode: String?, onBackClick: () -> Unit) {
    val viewModel: ProductDetailsViewModel = koinViewModel()

    LaunchedEffect(productId, barcode) {
        viewModel.onIntent(
            ViewIntent.InitialLoad(
                productId,
                barcode
            )
        )
    }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
                is ViewEvent.ShowError -> { /* Show Snackbar */ }
                ViewEvent.SuccessAdded -> onBackClick()
            }
        }
    ) { state ->
        ProductDetailsContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}
