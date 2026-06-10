package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.network.openfoodfacts.model.OFFProductNetModel
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel

internal class OffProductMapper : BaseMapper<OFFProductNetModel, ProductDomainModel> {

    override fun mapTo(model: OFFProductNetModel): ProductDomainModel {
        val nutrients = buildList {
            model.nutriments?.proteins100g?.let {
                add(NutrientDomainModel(NutrientTypeDomainModel.PROTEIN, it.toFloat()))
            }
            model.nutriments?.carbohydrates100g?.let {
                add(NutrientDomainModel(NutrientTypeDomainModel.CARBOHYDRATES, it.toFloat()))
            }
            model.nutriments?.fat100g?.let {
                add(NutrientDomainModel(NutrientTypeDomainModel.FATS, it.toFloat()))
            }
        }

        return ProductDomainModel(
            imageUrl = model.imageUrl,
            productName = model.productName,
            nutrients = nutrients,
            calories = model.nutriments?.energyKcal100g?.toFloat()
        )
    }
}
