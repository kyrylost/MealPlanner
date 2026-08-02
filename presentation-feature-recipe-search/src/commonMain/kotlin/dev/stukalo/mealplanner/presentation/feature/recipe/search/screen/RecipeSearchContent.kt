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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_minutes_short
import dev.stukalo.mealplanner.core.localization.common_recipe_search
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.BottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconFilter
import dev.stukalo.mealplanner.presentation.core.ui.widget.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.widget.recipe.RecipeCard
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.component.ActiveFilterChips
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.component.EmptyState
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.component.RecipeSearchBar
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewState
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RecipeSearchContent(
    state: ViewState,
    recipes: LazyPagingItems<RecipeDomainModel>,
    onIntent: (ViewIntent) -> Unit,
) {
    val hazeState = rememberHazeState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = Theme.size.compactScreenWidth),
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background),
        contentPadding = PaddingValues(
            bottom = Theme.spacing.space16 + (BottomBarHeight.current)
        ),
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            CommonHeader(
                title = stringResource(Res.string.common_recipe_search),
                leftIcon = IconBack,
                leftIconTint = Theme.color.iconPrimary,
                onLeftIconClick = { onIntent(ViewIntent.OnBackClick) },
                rightIcon = IconFilter,
                rightIconTint = Theme.color.primary,
                onRightIconClick = { onIntent(ViewIntent.OnFiltersClick) }
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            RecipeSearchBar(
                query = state.searchQuery,
                onQueryChange = { onIntent(ViewIntent.OnSearchQueryChange(it)) },
                modifier = Modifier.padding(horizontal = Theme.spacing.space16)
            )
        }

        state.filters?.let { filters ->
            item(span = { GridItemSpan(maxLineSpan) }) {
                ActiveFilterChips(
                    filters = filters,
                    onRemoveMealType = { type ->
                        val newTypes = filters.mealTypes.filter { it != type }
                        onIntent(ViewIntent.ApplyFilters(filters.copy(mealTypes = newTypes)))
                    },
                    onRemoveNutrient = { type ->
                        // Logic to clear specific nutrient ranges
                    },
                    modifier = Modifier.padding(horizontal = Theme.spacing.space16)
                )
            }
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
        } else if (recipes.loadState.refresh is LoadState.NotLoading && recipes.itemCount == 0) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                EmptyState(onClearFilters = { onIntent(ViewIntent.OnClearFilters) })
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
                        timeText = recipe.totalTime?.let { stringResource(Res.string.common_minutes_short, it) },
                        healthLabels = recipe.healthLabels,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.space16),
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
        RecipeSearchContent(
            state = ViewState(),
            recipes = flowOf(PagingData.from(emptyList<RecipeDomainModel>())).collectAsLazyPagingItems(),
            onIntent = {}
        )
    }
}
