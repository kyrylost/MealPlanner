package dev.stukalo.mealplanner.presentation.feature.product.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_grams_value
import dev.stukalo.mealplanner.core.localization.common_kcal
import dev.stukalo.mealplanner.core.localization.common_nutrient_with_unit
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.core.localization.common_unit_grams
import dev.stukalo.mealplanner.core.localization.common_unit_kcal
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.card.BlurredCard
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProductCard(
    product: ProductDomainModel,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BlurredCard(
        modifier = modifier,
        hazeState = hazeState,
        shape = Theme.shape.normalRoundedCornerShape
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(Theme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.space12)
        ) {
            Text(
                text = product.productName.orEmpty(),
                style = Theme.typography.bold16,
                color = Theme.color.text.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NutrientShortInfo(
                    label = stringResource(Res.string.common_calories),
                    value = product.calories?.toInt()?.let { stringResource(Res.string.common_kcal, it) } ?: "--",
                    unit = stringResource(Res.string.common_unit_kcal)
                )
                NutrientShortInfo(
                    label = stringResource(Res.string.common_proteins),
                    value =
                    product.nutrients
                        ?.find {
                            it.nutrientType == NutrientTypeDomainModel.PROTEIN
                        }?.amount
                        ?.toInt()
                        ?.let { stringResource(Res.string.common_grams_value, it) }
                        ?: "--",
                    unit = stringResource(Res.string.common_unit_grams)
                )
                NutrientShortInfo(
                    label = stringResource(Res.string.common_fats),
                    value =
                    product.nutrients
                        ?.find {
                            it.nutrientType == NutrientTypeDomainModel.FATS
                        }?.amount
                        ?.toInt()
                        ?.let { stringResource(Res.string.common_grams_value, it) }
                        ?: "--",
                    unit = stringResource(Res.string.common_unit_grams)
                )
                NutrientShortInfo(
                    label = stringResource(Res.string.common_carbs),
                    value =
                    product.nutrients
                        ?.find {
                            it.nutrientType == NutrientTypeDomainModel.CARBOHYDRATES
                        }?.amount
                        ?.toInt()
                        ?.let { stringResource(Res.string.common_grams_value, it) }
                        ?: "--",
                    unit = stringResource(Res.string.common_unit_grams)
                )
            }
        }
    }
}

@Composable
private fun NutrientShortInfo(label: String, value: String, unit: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = Theme.typography.bold14,
            color = Theme.color.text.primary
        )
        Text(
            text = stringResource(Res.string.common_nutrient_with_unit, label, unit),
            style = Theme.typography.regular12,
            color = Theme.color.text.secondary
        )
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    Theme {
        ProductCard(
            product =
            ProductDomainModel(
                productName = "Healthy Apple",
                calories = 52f,
                nutrients =
                listOf(
                    NutrientDomainModel(NutrientTypeDomainModel.PROTEIN, 0.3f),
                    NutrientDomainModel(NutrientTypeDomainModel.FATS, 0.2f),
                    NutrientDomainModel(NutrientTypeDomainModel.CARBOHYDRATES, 14f)
                )
            ),
            hazeState = rememberHazeState(),
            onClick = {}
        )
    }
}
