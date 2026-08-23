package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCSearchProductNetModel
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel

internal class FDCSearchProductMapper : BaseMapper<FDCSearchProductNetModel, ProductDomainModel> {
    override fun mapTo(model: FDCSearchProductNetModel): ProductDomainModel {
        val nutrients =
            model.foodNutrients?.mapNotNull { nutrient ->
                val type =
                    when (nutrient.nutrientId) {
                        FdcNutrientIds.PROTEIN_ID -> NutrientTypeDomainModel.PROTEIN
                        FdcNutrientIds.CARBS_ID -> NutrientTypeDomainModel.CARBOHYDRATES
                        FdcNutrientIds.FAT_ID -> NutrientTypeDomainModel.FATS
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
                ?.find { it.nutrientId == FdcNutrientIds.CALORIES_ID }
                ?.value
                ?.toFloat()
        val weight = model.servingSize?.toFloat()

        val caloriesTotal =
            if (weight != null && calories != null) {
                (calories * weight) / REFERENCE_WEIGHT_G
            } else {
                null
            }

        val nutrientsTotal =
            if (weight != null && nutrients != null) {
                nutrients.map {
                    it.copy(amount = (it.amount ?: 0f) * weight / REFERENCE_WEIGHT_G)
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
            brand = model.brandOwner,
            ingredients = model.ingredients,
            servingSize = if (model.servingSize != null && model.servingSizeUnit != null) {
                "${model.servingSize} ${model.servingSizeUnit}"
            } else {
                null
            }
        )
    }

    companion object {
        private const val REFERENCE_WEIGHT_G = 100f
    }
}
