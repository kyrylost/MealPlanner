package dev.stukalo.mealplanner.presentation.feature.productdetails.screen

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewEvent

/**
 * The product details screen logic holder.
 *
 * @param viewModel The view model for this screen.
 * @param onBackClick The callback for navigating back.
 */
@Composable
fun ProductDetailsScreen(viewModel: ProductDetailsViewModel, onBackClick: () -> Unit) {
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
