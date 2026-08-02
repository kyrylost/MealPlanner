package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductSearchScreen(
    onBackClick: () -> Unit,
    onNavigateToBarcodeScanner: () -> Unit,
) {
    val viewModel: ProductSearchViewModel = koinViewModel()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> onBackClick()
                ViewEvent.NavigateToBarcodeScanner -> onNavigateToBarcodeScanner()
            }
        }
    ) { state ->
        val products = state.productsFlow?.collectAsLazyPagingItems()

        ProductSearchContent(
            state = state,
            products = products,
            onIntent = viewModel::onIntent
        )
    }
}
