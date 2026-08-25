package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProductSearchScreen(
    onBackClick: () -> Unit,
    onNavigateToBarcodeScanner: () -> Unit,
    onNavigateToProductDetails: (String) -> Unit
) {
    val viewModel: ProductSearchViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
                ViewEvent.NavigateToBarcodeScanner -> onNavigateToBarcodeScanner()
                is ViewEvent.NavigateToProductDetails -> onNavigateToProductDetails(event.productId)
            }
        },
        snackbarHostState = snackbarHostState
    ) { state ->
        val products = state.productsFlow?.collectAsLazyPagingItems()

        ProductSearchContent(
            state = state,
            products = products,
            snackbarHostState = snackbarHostState,
            onIntent = viewModel::onIntent
        )
    }
}
