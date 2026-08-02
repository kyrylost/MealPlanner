package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamNutrientNetModel
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamNutrientsNetModel
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeNetModel
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel

internal class EdamamRecipeToProductMapper : BaseMapper<EdamamRecipeNetModel, ProductDomainModel> {
    override fun mapTo(model: EdamamRecipeNetModel): ProductDomainModel {
        val totalNutrients = mapNutrients(model.totalNutrients)
        val totalWeight = model.totalWeight?.toFloat() ?: 0f
        val caloriesTotal = model.calories?.toFloat()

        val (nutrientsPer100g, caloriesPer100g) =
            if (totalWeight > 0) {
                totalNutrients.map {
                    it.copy(amount = (it.amount ?: 0f) / totalWeight * 100f)
                } to (caloriesTotal?.div(totalWeight)?.times(100f))
            } else {
                null to null
            }

        return ProductDomainModel(
            id = model.uri?.substringAfterLast("_"),
            imageUrl = model.image,
            productName = model.label,
            nutrients = nutrientsPer100g,
            calories = caloriesPer100g,
            weight = totalWeight,
            caloriesTotal = caloriesTotal,
            nutrientsTotal = totalNutrients
        )
    }

    private fun mapNutrients(nutrients: EdamamNutrientsNetModel?): List<NutrientDomainModel> = buildList {
        nutrients?.protein?.let { add(it.toDomain(NutrientTypeDomainModel.PROTEIN)) }
        nutrients?.carbs?.let { add(it.toDomain(NutrientTypeDomainModel.CARBOHYDRATES)) }
        nutrients?.fat?.let { add(it.toDomain(NutrientTypeDomainModel.FATS)) }
    }

    private fun EdamamNutrientNetModel.toDomain(type: NutrientTypeDomainModel): NutrientDomainModel =
        NutrientDomainModel(
            nutrientType = type,
            amount = quantity?.toFloat()
        )
}
