package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCFoodNetModel
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel

internal class FdcProductMapper : BaseMapper<FDCFoodNetModel, ProductDomainModel> {
    override fun mapTo(model: FDCFoodNetModel): ProductDomainModel {
        val nutrients =
            model.foodNutrients?.mapNotNull { nutrient ->
                val type =
                    when (nutrient.nutrientId) {
                        1003 -> NutrientTypeDomainModel.PROTEIN
                        1005 -> NutrientTypeDomainModel.CARBOHYDRATES
                        1004 -> NutrientTypeDomainModel.FATS
                        else -> null
                    }
                type?.let {
                    NutrientDomainModel(
                        nutrientType = it,
                        amount = nutrient.value.toFloat()
                    )
                }
            }

        val calories =
            model.foodNutrients
                ?.find { it.nutrientId == 1008 }
                ?.value
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
            nutrientsTotal = nutrientsTotal
        )
    }
}
