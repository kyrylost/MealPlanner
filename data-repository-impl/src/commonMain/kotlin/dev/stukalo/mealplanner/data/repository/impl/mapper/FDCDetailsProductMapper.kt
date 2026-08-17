package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCProductDetailsResponseNetModel
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel

internal class FDCDetailsProductMapper : BaseMapper<FDCProductDetailsResponseNetModel, ProductDomainModel> {
    override fun mapTo(model: FDCProductDetailsResponseNetModel): ProductDomainModel {
        val nutrients =
            model.foodNutrients?.mapNotNull { nutrient ->
                val nutrientId = nutrient.nutrient?.id
                val value = nutrient.amount

                if (nutrientId == null || value == null) return@mapNotNull null

                val type =
                    when (nutrientId) {
                        1003 -> NutrientTypeDomainModel.PROTEIN
                        1005 -> NutrientTypeDomainModel.CARBOHYDRATES
                        1004 -> NutrientTypeDomainModel.FATS
                        else -> null
                    }
                type?.let {
                    NutrientDomainModel(
                        nutrientType = it,
                        amount = value.toFloat()
                    )
                }
            }

        val calories =
            model.foodNutrients
                ?.find { it.nutrient?.id == 1008 }
                ?.amount
                ?.toFloat()

        val weight = model.servingSize?.toFloat()

        val caloriesTotal =
            if (weight != null && calories != null) {
                (calories * weight) / 100f
            } else {
                null
            }

        val nutrientsTotal =
            if (weight != null && nutrients != null) {
                nutrients.map {
                    it.copy(amount = (it.amount ?: 0f) * weight / 100f)
                }
            } else {
                null
            }

        return ProductDomainModel(
            id = model.fdcId.toString(),
            productName = model.description,
            nutrients = nutrients,
            calories = calories,
            weight = weight,
            caloriesTotal = caloriesTotal,
            nutrientsTotal = nutrientsTotal,
            brand = model.brandOwner ?: model.brandName,
            ingredients = model.ingredients,
            servingSize = if (model.servingSize != null && model.servingSizeUnit != null) {
                "${model.servingSize} ${model.servingSizeUnit}"
            } else {
                null
            }
        )
    }
}
