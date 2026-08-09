package dev.stukalo.mealplanner.presentation.feature.product.search.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import kotlinx.coroutines.flow.flowOf

/**
 * A component that displays a list of products with paging support.
 *
 * @param products The paging items for the products list.
 * @param hazeState The state for the haze effect (blur).
 * @param onProductClick The callback for when a product is clicked.
 */
@Composable
fun ProductsList(
    products: LazyPagingItems<ProductDomainModel>?,
    hazeState: HazeState,
    onProductClick: (ProductDomainModel) -> Unit
) {
    if (products == null) return

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding =
        PaddingValues(
            top = Theme.spacing.space16,
            start = Theme.spacing.space16,
            end = Theme.spacing.space16,
            bottom = Theme.spacing.space16 + LocalBottomBarHeight.current
        ),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        items(
            count = products.itemCount,
            key = products.itemKey { it.id ?: it.productName.orEmpty() }
        ) { index ->
            val product = products[index]
            if (product != null) {
                ProductCard(
                    product = product,
                    hazeState = hazeState,
                    onClick = { onProductClick(product) }
                )
            }
        }

        if (products.loadState.append is LoadState.Loading) {
            item {
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Theme.spacing.space16),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Theme.color.primary)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProductsListPreview() {
    Theme {
        ProductsList(
            products =
            flowOf(
                PagingData.from(
                    listOf(
                        ProductDomainModel(id = "1", productName = "Apple", calories = 52f),
                        ProductDomainModel(id = "2", productName = "Banana", calories = 89f)
                    )
                )
            ).collectAsLazyPagingItems(),
            hazeState = rememberHazeState(),
            onProductClick = {}
        )
    }
}
