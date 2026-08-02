package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.common_value_placeholder
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_subtitle
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_title
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBarcodeScanner
import dev.stukalo.mealplanner.presentation.core.ui.widget.picker.ValueEditDialog
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.component.ProductSearchBar
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.component.ProductsList
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.component.SuggestionsList
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

/**
 * The content of the product search screen.
 *
 * @param state The current view state.
 * @param products The paging items for the products list.
 * @param onIntent The callback for view intents.
 */
@Composable
internal fun ProductSearchContent(
    state: ViewState,
    products: LazyPagingItems<ProductDomainModel>?,
    onIntent: (ViewIntent) -> Unit
) {
    val hazeState = rememberHazeState()
    var selectedProduct by remember { mutableStateOf<ProductDomainModel?>(null) }

    if (selectedProduct != null) {
        ValueEditDialog(
            initialValue = "",
            onDismissRequest = { selectedProduct = null },
            onConfirm = { weightStr ->
                weightStr.toFloatOrNull()?.let { weight ->
                    selectedProduct?.let { product ->
                        onIntent(ViewIntent.OnLogProduct(product, weight))
                    }
                }
                selectedProduct = null
            },
            title = stringResource(Res.string.home_consumed_amount_title),
            message = stringResource(Res.string.home_consumed_amount_subtitle),
            placeholder = stringResource(Res.string.common_value_placeholder),
            confirmLabel = stringResource(Res.string.common_ok),
            dismissLabel = stringResource(Res.string.common_cancel)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            ProductSearchBar(
                query = state.query,
                onQueryChange = { onIntent(ViewIntent.OnQueryChange(it)) },
                modifier = Modifier.padding(horizontal = Theme.spacing.space16)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (state.suggestions.isNotEmpty() && state.query.isNotEmpty()) {
                    SuggestionsList(
                        suggestions = state.suggestions,
                        onSuggestionClick = { onIntent(ViewIntent.OnSuggestionClick(it)) }
                    )
                } else {
                    ProductsList(
                        products = products,
                        hazeState = hazeState,
                        onProductClick = { selectedProduct = it }
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Theme.color.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { onIntent(ViewIntent.OnBarcodeScannerClick) },
            containerColor = Theme.color.primary,
            contentColor = Theme.color.textOnPrimary,
            shape = CircleShape,
            modifier =
            Modifier
                .align(Alignment.BottomEnd)
                .padding(Theme.spacing.space16)
                .padding(bottom = LocalBottomBarHeight.current)
        ) {
            Icon(
                imageVector = IconBarcodeScanner,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun ProductSearchContentPreview() {
    Theme {
        Surface(color = Theme.color.background) {
            ProductSearchContent(
                state = ViewState(query = "Apple"),
                products = null,
                onIntent = {}
            )
        }
    }
}
