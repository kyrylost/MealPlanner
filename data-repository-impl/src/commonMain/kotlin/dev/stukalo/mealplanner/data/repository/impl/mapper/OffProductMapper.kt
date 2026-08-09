package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.network.openfoodfacts.model.OFFProductResponseNetModel
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.food.quality.NovaGroup
import dev.stukalo.mealplanner.domain.model.food.quality.NutriScore
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel

/**
 * A mapper that converts [OFFProductResponseNetModel] from the Open Food Facts API
 * into the domain-specific [ProductDomainModel].
 */
internal class OffProductMapper : BaseMapper<OFFProductResponseNetModel, ProductDomainModel> {
    override fun mapTo(model: OFFProductResponseNetModel): ProductDomainModel {
        val product = model.product
        val nutrients =
            buildList {
                product?.nutriments?.proteins100g?.let {
                    add(NutrientDomainModel(NutrientTypeDomainModel.PROTEIN, it.toFloat()))
                }
                product?.nutriments?.carbohydrates100g?.let {
                    add(NutrientDomainModel(NutrientTypeDomainModel.CARBOHYDRATES, it.toFloat()))
                }
                product?.nutriments?.fat100g?.let {
                    add(NutrientDomainModel(NutrientTypeDomainModel.FATS, it.toFloat()))
                }
            }

        return ProductDomainModel(
            id = model.code,
            imageUrl = product?.imageUrl,
            productName = product?.productName,
            nutrients = nutrients,
            calories = product?.nutriments?.energyKcal100g?.toFloat(),
            brand = product?.brands,
            ingredients = product?.ingredientsText,
            servingSize = product?.servingSize,
            nutriScore = product?.nutriScoreGrade?.let { grade ->
                runCatching { NutriScore.valueOf(grade.uppercase()) }.getOrNull()
            },
            novaGroup = product?.novaGroup?.let { group ->
                when (group) {
                    1 -> NovaGroup.GROUP_1
                    2 -> NovaGroup.GROUP_2
                    3 -> NovaGroup.GROUP_3
                    4 -> NovaGroup.GROUP_4
                    else -> null
                }
            }
        )
    }
}
