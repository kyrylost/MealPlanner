package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.BottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.widget.recipe.RecipeCard
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.component.RecipeSearchHeader
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewState

@Composable
internal fun RecipeSearchContent(
    state: ViewState,
    recipes: LazyPagingItems<RecipeDomainModel>,
    onIntent: (ViewIntent) -> Unit,
) {
    val hazeState = rememberHazeState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 328.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background),
        contentPadding = PaddingValues(
            start = Theme.spacing.space16,
            end = Theme.spacing.space16,
            bottom = Theme.spacing.space16 + (BottomBarHeight.current)
        ),
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            RecipeSearchHeader(
                onFiltersClick = { onIntent(ViewIntent.OnFiltersClick) }
            )
        }

        if (recipes.loadState.refresh is LoadState.Loading && recipes.itemCount == 0) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Theme.spacing.space32),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Theme.color.primary)
                }
            }
        } else {
            items(
                count = recipes.itemCount,
                key = recipes.itemKey { it.id.orEmpty() },
                contentType = recipes.itemContentType { "recipe" }
            ) { index ->
                val recipe = recipes[index]
                if (recipe != null) {
                    RecipeCard(
                        title = recipe.product.productName.orEmpty(),
                        imageUrl = recipe.product.imageUrl,
                        totalTime = recipe.totalTime,
                        healthLabels = recipe.healthLabels,
                        modifier = Modifier.fillMaxWidth(),
                        hazeState = hazeState,
                        onClick = {
                            onIntent(ViewIntent.OnRecipeClick(recipe.id.orEmpty()))
                        }
                    )
                }
            }

            if (recipes.loadState.append is LoadState.Loading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Theme.spacing.space16),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Theme.color.primary)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecipeSearchContentPreview() {
    Theme {
    }
}
