package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_no_results
import dev.stukalo.mealplanner.core.localization.common_no_results_desc
import dev.stukalo.mealplanner.core.localization.common_product_search
import dev.stukalo.mealplanner.core.localization.product_search_initial_desc
import dev.stukalo.mealplanner.core.localization.product_search_initial_title
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.component.empty.CommonEmptyState
import dev.stukalo.mealplanner.presentation.core.ui.component.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBarcodeScanner
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconSearch
import dev.stukalo.mealplanner.presentation.feature.product.search.component.ProductSearchBar
import dev.stukalo.mealplanner.presentation.feature.product.search.component.ProductsList
import dev.stukalo.mealplanner.presentation.feature.product.search.component.SuggestionsList
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
        ) {
            CommonHeader(
                title = stringResource(Res.string.common_product_search)
            )

            Spacer(modifier = Modifier.height(Theme.spacing.space16))

            ProductSearchBar(
                query = state.query,
                onQueryChange = { onIntent(ViewIntent.OnQueryChange(it)) },
                onAction = { onIntent(ViewIntent.OnSearchClick) },
                modifier = Modifier.padding(horizontal = Theme.spacing.space16)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    products == null -> {
                        CommonEmptyState(
                            title = stringResource(Res.string.product_search_initial_title),
                            description = stringResource(Res.string.product_search_initial_desc),
                            icon = IconSearch,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    products.loadState.refresh is LoadState.Loading -> {
                        CircularProgressIndicator(
                            color = Theme.color.brand.primary,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    products.loadState.refresh is LoadState.NotLoading && products.itemCount == 0 -> {
                        CommonEmptyState(
                            title = stringResource(Res.string.common_no_results),
                            description = stringResource(Res.string.common_no_results_desc),
                            icon = IconSearch,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        ProductsList(
                            products = products,
                            hazeState = hazeState,
                            onProductClick = { onIntent(ViewIntent.OnProductClick(it)) }
                        )
                    }
                }

                if (state.suggestions.isNotEmpty() && state.query.isNotEmpty()) {
                    SuggestionsList(
                        suggestions = state.suggestions,
                        onSuggestionClick = { onIntent(ViewIntent.OnSuggestionClick(it)) }
                    )
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = Theme.color.brand.primary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { onIntent(ViewIntent.OnBarcodeScannerClick) },
            containerColor = Theme.color.brand.primary,
            contentColor = Theme.color.text.onPrimary,
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
        Surface(color = Theme.color.background.primary) {
            ProductSearchContent(
                state = ViewState(query = "Apple"),
                products = null,
                onIntent = {}
            )
        }
    }
}
