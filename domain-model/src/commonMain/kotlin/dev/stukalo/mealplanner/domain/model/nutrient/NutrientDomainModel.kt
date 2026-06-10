package dev.stukalo.mealplanner.domain.model.nutrient

data class NutrientDomainModel(
    val nutrientType: NutrientTypeDomainModel? = null,
    val amount: Float? = null,
)
