package dev.stukalo.mealplanner.presentation.feature.productdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.common_value_placeholder
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_subtitle
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_title
import dev.stukalo.mealplanner.core.localization.product_details_add_to_diary
import dev.stukalo.mealplanner.core.localization.product_details_ingredients
import dev.stukalo.mealplanner.core.localization.product_details_not_found
import dev.stukalo.mealplanner.core.localization.product_details_serving_size_value
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.food.quality.NovaGroup
import dev.stukalo.mealplanner.domain.model.food.quality.NutriScore
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.chip.InfoChip
import dev.stukalo.mealplanner.presentation.core.ui.widget.dialog.ValueEditDialog
import dev.stukalo.mealplanner.presentation.core.ui.widget.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.widget.nutrition.NutritionSummary
import dev.stukalo.mealplanner.presentation.core.ui.widget.quality.NovaGroupBadge
import dev.stukalo.mealplanner.presentation.core.ui.widget.quality.NutriScoreBadge
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.productdetails.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

/**
 * The main UI content for the Product Details screen.
 *
 * This component is stateless and follows the MVI pattern, receiving its state
 * via [state] and communicating user actions through [onIntent].
 *
 * @param state The current [ViewState] to render.
 * @param onIntent A lambda to handle user [ViewIntent]s.
 */
@Composable
fun ProductDetailsContent(state: ViewState, onIntent: (ViewIntent) -> Unit) {
    val hazeState = rememberHazeState()
    var buttonHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    if (state.isDialogVisible) {
        ValueEditDialog(
            initialValue = "",
            onDismissRequest = { onIntent(ViewIntent.OnDismissDialog) },
            onConfirm = { weightStr ->
                weightStr.toFloatOrNull()?.let { weight ->
                    onIntent(ViewIntent.OnConfirmLog(weight))
                } ?: onIntent(ViewIntent.OnDismissDialog)
            },
            title = stringResource(Res.string.home_consumed_amount_title),
            message = stringResource(Res.string.home_consumed_amount_subtitle),
            placeholder = stringResource(Res.string.common_value_placeholder),
            confirmLabel = stringResource(Res.string.common_ok),
            dismissLabel = stringResource(Res.string.common_cancel)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = Theme.color.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.product != null) {
            val product = state.product

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(Theme.aspect.productDetailsImage)
                            .background(Theme.color.backgroundSecondary),
                        contentScale = ContentScale.Crop
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Theme.spacing.space16),
                        verticalArrangement = Arrangement.spacedBy(Theme.spacing.space24)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)) {
                            Text(
                                text = product.productName.orEmpty(),
                                style = Theme.typography.bold36,
                                color = Theme.color.textPrimary
                            )

                            product.brand?.let { brandName ->
                                Text(
                                    text = brandName,
                                    style = Theme.typography.semibold16,
                                    color = Theme.color.textSecondary
                                )
                            }

                            product.servingSize?.let { size ->
                                InfoChip(
                                    text = stringResource(Res.string.product_details_serving_size_value, size)
                                )
                            }

                            if (product.nutriScore != null || product.novaGroup != null) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space8),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    product.nutriScore?.let { NutriScoreBadge(it) }
                                    product.novaGroup?.let { NovaGroupBadge(it) }
                                }
                            }
                        }

                        NutritionSummary(
                            calories = product.calories ?: 0f,
                            protein = product.nutrients
                                ?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
                                ?.amount ?: 0f,
                            fats = product.nutrients
                                ?.find { it.nutrientType == NutrientTypeDomainModel.FATS }
                                ?.amount ?: 0f,
                            carbs = product.nutrients
                                ?.find { it.nutrientType == NutrientTypeDomainModel.CARBOHYDRATES }
                                ?.amount ?: 0f,
                            hazeState = hazeState
                        )

                        product.ingredients?.let { ingredients ->
                            Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space12)) {
                                Text(
                                    text = stringResource(Res.string.product_details_ingredients),
                                    style = Theme.typography.bold16,
                                    color = Theme.color.textPrimary
                                )
                                Text(
                                    text = ingredients,
                                    style = Theme.typography.regular14,
                                    color = Theme.color.textSecondary
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .height(buttonHeight + Theme.spacing.space24)
                    )
                }
            }

            CommonHeader(
                title = "",
                leftIcon = IconBack,
                onLeftIconClick = { onIntent(ViewIntent.OnBackClick) }
            )

            PrimaryButton(
                text = stringResource(Res.string.product_details_add_to_diary),
                onClick = { onIntent(ViewIntent.OnAddConsumedClick) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(Theme.spacing.space16)
                    .navigationBarsPadding()
                    .onGloballyPositioned {
                        buttonHeight = with(density) { it.size.height.toDp() }
                    }
            )
        } else if (state.error != null) {
            Text(
                text = stringResource(Res.string.product_details_not_found),
                color = Theme.color.textSecondary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun ProductDetailsContentPreview() {
    Theme {
        Surface(color = Theme.color.background) {
            ProductDetailsContent(
                state = ViewState(
                    isLoading = false,
                    product = ProductDomainModel(
                        id = "1",
                        productName = "Raw Chicken Breast",
                        brand = "Premium Poultry",
                        servingSize = "100g",
                        ingredients = "Chicken Breast, Salt, Natural Flavors",
                        nutriScore = NutriScore.A,
                        novaGroup = NovaGroup.GROUP_1,
                        calories = 165f,
                        nutrients = listOf(
                            NutrientDomainModel(NutrientTypeDomainModel.PROTEIN, 31f),
                            NutrientDomainModel(NutrientTypeDomainModel.FATS, 3.6f),
                            NutrientDomainModel(NutrientTypeDomainModel.CARBOHYDRATES, 0f)
                        ),
                        imageUrl = "https://example.com/chicken.jpg"
                    )
                ),
                onIntent = {}
            )
        }
    }
}
