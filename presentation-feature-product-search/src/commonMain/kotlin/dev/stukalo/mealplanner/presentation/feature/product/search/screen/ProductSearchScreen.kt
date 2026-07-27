package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductSearchScreen() {
    val viewModel: ProductSearchViewModel = koinViewModel()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { /* Handle events */ }
    ) { state ->
        ProductSearchContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}
