package dev.stukalo.mealplanner.domain.model.food

import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel

/**
 * Represents a nutritional product or recipe in the domain layer.
 *
 * **Important:** Base nutritional values ([calories] and [nutrients]) are standardized
 * **per 100g** of the product. To get the values for the entire product, use the
 * optional "total" fields ([caloriesTotal] and [nutrientsTotal]), which are calculated
 * based on the product's [weight].
 *
 * @property imageUrl The URL pointing to the image of the product, if available.
 * @property productName The display name of the product.
 * @property nutrients The list of nutrients contained in **100g** of the product.
 * @property calories The energy value (in kcal) per **100g** of the product.
 * @property weight The total weight of the product in grams.
 * @property caloriesTotal The total energy value (in kcal) for the entire product weight.
 * @property nutrientsTotal The list of nutrients for the entire product weight.
 */
data class ProductDomainModel(
    val id: String? = null,
    val imageUrl: String? = null,
    val productName: String? = null,
    val nutrients: List<NutrientDomainModel>? = null,
    val calories: Float? = null,
    // Optional fields
    val weight: Float? = null,
    val caloriesTotal: Float? = null,
    val nutrientsTotal: List<NutrientDomainModel>? = null
)
