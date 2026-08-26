package dev.stukalo.mealplanner.presentation.core.navigation.model

import kotlinx.serialization.Serializable

/**
 * Navigation model for recipe filters.
 * Decoupled from domain to avoid direct dependency in navigation.
 *
 * @property minCalories Minimum calories.
 * @property maxCalories Maximum calories.
 * @property minProteins Minimum proteins.
 * @property maxProteins Maximum proteins.
 * @property minFats Minimum fats.
 * @property maxFats Maximum fats.
 * @property minCarbs Minimum carbohydrates.
 * @property maxCarbs Maximum carbohydrates.
 * @property mealTypes List of selected meal types.
 */
@Serializable
data class FilterNavModel(
    val minCalories: Int? = null,
    val maxCalories: Int? = null,
    val minProteins: Int? = null,
    val maxProteins: Int? = null,
    val minFats: Int? = null,
    val maxFats: Int? = null,
    val minCarbs: Int? = null,
    val maxCarbs: Int? = null,
    val mealTypes: List<MealTypeNavModel> = emptyList()
)
