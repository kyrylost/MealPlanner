package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_minutes_short
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.core.localization.common_show_all
import dev.stukalo.mealplanner.core.localization.common_unit_grams
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_subtitle
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_title
import dev.stukalo.mealplanner.core.localization.home_recommended_for_today
import dev.stukalo.mealplanner.core.localization.home_steps
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.widget.picker.ValueEditDialog
import dev.stukalo.mealplanner.presentation.core.ui.widget.recipe.RecipeCard
import dev.stukalo.mealplanner.presentation.feature.home.component.ActivityGauge
import dev.stukalo.mealplanner.presentation.feature.home.component.BackgroundCircles
import dev.stukalo.mealplanner.presentation.feature.home.component.CaloriesProgressCard
import dev.stukalo.mealplanner.presentation.feature.home.component.HomeHeader
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.NutrientType
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewState
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeContent(
    state: ViewState,
    recommendedRecipes: LazyPagingItems<RecipeDomainModel>,
    onIntent: (ViewIntent) -> Unit,
) {
    val hazeState = rememberHazeState()
    var activeNutrientType by remember { mutableStateOf<NutrientType?>(null) }

    activeNutrientType?.let { type ->
        ValueEditDialog(
            initialValue = "",
            onDismissRequest = { activeNutrientType = null },
            onConfirm = { amount ->
                amount.toFloatOrNull()?.let {
                    onIntent(ViewIntent.OnAddNutrient(type, it))
                }
            },
            title = stringResource(Res.string.home_consumed_amount_title),
            message = stringResource(Res.string.home_consumed_amount_subtitle),
            placeholder = "0.0",
            confirmLabel = stringResource(Res.string.common_ok),
            dismissLabel = stringResource(Res.string.common_cancel)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundCircles(hazeState)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = Theme.size.compactScreenWidth),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = Theme.spacing.space16,
                end = Theme.spacing.space16,
                bottom = Theme.spacing.space16
            ),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16),
        ) {
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Spacer(modifier = Modifier.height(Theme.spacing.space48))
            }

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                HomeHeader(
                    userName = state.userName,
                    modifier = Modifier.fillMaxWidth().statusBarsPadding()
                )
            }

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Spacer(modifier = Modifier.height(Theme.spacing.space128))
            }

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                CaloriesProgressCard(
                    currentCalories = state.currentCalories,
                    targetCalories = state.targetCalories,
                    hazeState = hazeState
                )
            }

            // Activity Gauges
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Theme.spacing.space24),
                    horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16),
                    verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16),
                    maxItemsInEachRow = 2
                ) {
                    val gaugeModifier = Modifier.weight(1f)
                    ActivityGauge(
                        current = state.proteins,
                        target = state.proteinsTarget,
                        label = stringResource(Res.string.common_proteins),
                        unit = stringResource(Res.string.common_unit_grams),
                        hazeState = hazeState,
                        modifier = gaugeModifier,
                        onClick = { activeNutrientType = NutrientType.PROTEINS }
                    )
                    ActivityGauge(
                        current = state.fats,
                        target = state.fatsTarget,
                        label = stringResource(Res.string.common_fats),
                        unit = stringResource(Res.string.common_unit_grams),
                        hazeState = hazeState,
                        modifier = gaugeModifier,
                        onClick = { activeNutrientType = NutrientType.FATS }
                    )
                    ActivityGauge(
                        current = state.carbs,
                        target = state.carbsTarget,
                        label = stringResource(Res.string.common_carbs),
                        unit = stringResource(Res.string.common_unit_grams),
                        hazeState = hazeState,
                        modifier = gaugeModifier,
                        onClick = { activeNutrientType = NutrientType.CARBS }
                    )
                    ActivityGauge(
                        current = state.steps,
                        target = state.stepsTarget,
                        label = stringResource(Res.string.home_steps),
                        unit = "",
                        hazeState = hazeState,
                        modifier = gaugeModifier
                    )
                }
            }

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Spacer(modifier = Modifier.height(Theme.spacing.space48))
            }

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.home_recommended_for_today),
                        style = Theme.typography.bold14,
                        color = Theme.color.textPrimary
                    )
                    Text(
                        text = stringResource(Res.string.common_show_all),
                        style = Theme.typography.bold14,
                        color = Theme.color.primary,
                        modifier = Modifier.clickable { onIntent(ViewIntent.OnShowAllRecipesClick) }
                    )
                }
            }

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Spacer(modifier = Modifier.height(Theme.spacing.space16))
            }

            // Recommended Recipes
            if (recommendedRecipes.loadState.refresh is LoadState.Loading && recommendedRecipes.itemCount == 0) {
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
                    count = recommendedRecipes.itemCount,
                    key = recommendedRecipes.itemKey { it.id.orEmpty() },
                    contentType = recommendedRecipes.itemContentType { "recipe" },
                ) { index ->
                    val recipe = recommendedRecipes[index]
                    if (recipe != null) {
                        RecipeCard(
                            title = recipe.product.productName.orEmpty(),
                            imageUrl = recipe.product.imageUrl,
                            timeText = recipe.totalTime?.let { stringResource(Res.string.common_minutes_short, it) },
                            healthLabels = recipe.healthLabels,
                            modifier = Modifier.fillMaxWidth(),
                            hazeState = hazeState,
                            onClick = { onIntent(ViewIntent.OnRecipeClick(recipe.id.orEmpty())) }
                        )
                    }
                }

                if (recommendedRecipes.loadState.append is LoadState.Loading) {
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

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Spacer(modifier = Modifier.height(LocalBottomBarHeight.current))
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    Theme {
        Surface(color = Theme.color.background) {
            HomeContent(
                state = ViewState(userName = "User"),
                recommendedRecipes = flowOf(
                    PagingData.from(
                        listOf(
                            RecipeDomainModel(product = ProductDomainModel(productName = "Salmon Salad")),
                            RecipeDomainModel(product = ProductDomainModel(productName = "Beef Stew"))
                        )
                    )
                ).collectAsLazyPagingItems(),
                onIntent = {}
            )
        }
    }
}
