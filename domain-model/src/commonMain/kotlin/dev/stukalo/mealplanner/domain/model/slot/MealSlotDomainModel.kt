package dev.stukalo.mealplanner.domain.model.slot

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import kotlinx.datetime.LocalTime

data class MealSlotDomainModel(
    val id: Int,
    val name: String,
    val startTime: LocalTime,
    val proteinsPercentage: Int,
    val fatsPercentage: Int,
    val carbsPercentage: Int,
    val mealTypes: List<MealTypeDomainModel>,
    val isConsumed: Boolean
)
