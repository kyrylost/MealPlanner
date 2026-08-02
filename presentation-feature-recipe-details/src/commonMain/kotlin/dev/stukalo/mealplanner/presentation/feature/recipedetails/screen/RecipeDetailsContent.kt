package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_bullet_item
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_minutes_short
import dev.stukalo.mealplanner.core.localization.common_numbered_item
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.common_servings
import dev.stukalo.mealplanner.core.localization.common_value_placeholder
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_subtitle
import dev.stukalo.mealplanner.core.localization.home_consumed_amount_title
import dev.stukalo.mealplanner.core.localization.recipe_details_ingredients
import dev.stukalo.mealplanner.core.localization.recipe_details_instructions
import dev.stukalo.mealplanner.core.localization.recipe_details_log_meal
import dev.stukalo.mealplanner.core.localization.recipe_details_not_found
import dev.stukalo.mealplanner.core.localization.recipe_details_view_prep
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconClock
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.widget.picker.ValueEditDialog
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.component.HealthLabelChip
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.component.InfoChip
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.component.NutritionSummary
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

/**
 * Stateless content for the Recipe Details screen.
 *
 * @param state Current view state.
 * @param onIntent Callback to handle user intents.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun RecipeDetailsContent(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit
) {
    val uriHandler = LocalUriHandler.current
    val hazeState = rememberHazeState()
    var showWeightDialog by remember { mutableStateOf(false) }
    var buttonHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    if (showWeightDialog) {
        ValueEditDialog(
            initialValue = "",
            onDismissRequest = { showWeightDialog = false },
            onConfirm = { weightStr ->
                weightStr.toFloatOrNull()?.let { weight ->
                    onIntent(ViewIntent.OnLogMealClick(weight))
                }
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
        } else if (state.recipe != null) {
            val currentRecipe = state.recipe
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = LocalBottomBarHeight.current)
            ) {
                item {
                    AsyncImage(
                        model = currentRecipe.product.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(Theme.aspect.recipeDetailsImage),
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
                                text = currentRecipe.product.productName.orEmpty(),
                                style = Theme.typography.bold36,
                                color = Theme.color.textPrimary
                            )
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
                            ) {
                                InfoChip(
                                    icon = IconClock,
                                    text = currentRecipe.totalTime?.let { stringResource(Res.string.common_minutes_short, it) } ?: "--"
                                )
                                InfoChip(
                                    text = currentRecipe.servings?.let { stringResource(Res.string.common_servings, it) } ?: "--"
                                )
                            }
                        }

                        NutritionSummary(
                            calories = currentRecipe.product.caloriesTotal ?: 0f,
                            protein = currentRecipe.product.nutrientsTotal?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }?.amount ?: 0f,
                            fats = currentRecipe.product.nutrientsTotal?.find { it.nutrientType == NutrientTypeDomainModel.FATS }?.amount ?: 0f,
                            carbs = currentRecipe.product.nutrientsTotal?.find { it.nutrientType == NutrientTypeDomainModel.CARBOHYDRATES }?.amount ?: 0f,
                            hazeState = hazeState
                        )

                        currentRecipe.healthLabels?.let { labels ->
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space8),
                                verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)
                            ) {
                                labels.forEach { label ->
                                    HealthLabelChip(label)
                                }
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space12)) {
                            Text(
                                text = stringResource(Res.string.recipe_details_ingredients),
                                style = Theme.typography.bold16,
                                color = Theme.color.textPrimary
                            )
                            currentRecipe.ingredientLines?.forEach { line ->
                                Text(
                                    text = stringResource(Res.string.common_bullet_item, line),
                                    style = Theme.typography.regular14,
                                    color = Theme.color.textSecondary
                                )
                            }
                        }

                        currentRecipe.instructionLines?.takeIf { it.isNotEmpty() }?.let { instructions ->
                            Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space12)) {
                                Text(
                                    text = stringResource(Res.string.recipe_details_instructions),
                                    style = Theme.typography.bold16,
                                    color = Theme.color.textPrimary
                                )
                                instructions.forEachIndexed { index, line ->
                                    Text(
                                        text = stringResource(Res.string.common_numbered_item, index + 1, line),
                                        style = Theme.typography.regular14,
                                        color = Theme.color.textSecondary
                                    )
                                }
                            }
                        }

                        currentRecipe.url?.let { url ->
                            Text(
                                text = stringResource(Res.string.recipe_details_view_prep),
                                style = Theme.typography.bold14.copy(
                                    color = Theme.color.primary,
                                    textDecoration = TextDecoration.Underline
                                ),
                                modifier = Modifier.clickable { uriHandler.openUri(url) }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(buttonHeight + Theme.spacing.space24))
                }
            }

            CommonHeader(
                title = "",
                leftIcon = IconBack,
                onLeftIconClick = { onIntent(ViewIntent.OnBackClick) }
            )

            PrimaryButton(
                text = stringResource(Res.string.recipe_details_log_meal),
                onClick = { showWeightDialog = true },
                corner = Theme.radius.radius24,
                textStyle = Theme.typography.bold14,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(Theme.spacing.space16)
                    .navigationBarsPadding()
                    .onGloballyPositioned {
                        buttonHeight = with(density) { it.size.height.toDp() }
                    },
            )
        } else {
            Text(
                text = stringResource(Res.string.recipe_details_not_found),
                color = Theme.color.textSecondary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun RecipeDetailsContentPreview() {
    Theme {
        RecipeDetailsContent(
            state = ViewState(
                recipe = RecipeDomainModel(
                    id = "1",
                    product = ProductDomainModel(
                        productName = "Healthy Salmon Salad",
                        imageUrl = "https://www.edamam.com/web-img/70a/70af3664d422998a449174092b37803f.jpg",
                        caloriesTotal = 450f,
                        nutrientsTotal = listOf(
                            NutrientDomainModel(NutrientTypeDomainModel.PROTEIN, 35f),
                            NutrientDomainModel(NutrientTypeDomainModel.FATS, 20f),
                            NutrientDomainModel(NutrientTypeDomainModel.CARBOHYDRATES, 15f)
                        )
                    ),
                    servings = 2,
                    totalTime = 25,
                    ingredientLines = listOf(
                        "200g Fresh Salmon",
                        "100g Mixed Greens",
                        "1 Avocado",
                        "Olive Oil"
                    ),
                    instructionLines = listOf(
                        "Season the salmon with salt and pepper.",
                        "Pan-sear the salmon for 4 minutes on each side.",
                        "Toss the greens with avocado and olive oil.",
                        "Top with the salmon and serve."
                    ),
                    healthLabels = listOf("Gluten-Free", "Keto-Friendly", "Low-Sugar"),
                    url = "https://example.com/recipe"
                )
            ),
            onIntent = {}
        )
    }
}
