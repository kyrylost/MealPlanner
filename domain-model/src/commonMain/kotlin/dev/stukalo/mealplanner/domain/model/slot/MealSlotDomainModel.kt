package dev.stukalo.mealplanner.domain.model.slot

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import kotlinx.datetime.LocalTime

/**
 * Domain model representing a meal slot in the daily schedule.
 *
 * @property id Unique identifier of the slot.
 * @property startTime The scheduled time for the meal.
 * @property proteinsPercentage Target percentage of daily protein intake for this slot.
 * @property fatsPercentage Target percentage of daily fat intake for this slot.
 * @property carbsPercentage Target percentage of daily carbohydrate intake for this slot.
 * @property mealType The type of meal (e.g., Breakfast, Lunch, Dinner).
 * @property isConsumed Whether the meal has been tracked as consumed for the current day.
 */
data class MealSlotDomainModel(
    val id: Int,
    val startTime: LocalTime,
    val proteinsPercentage: Int,
    val fatsPercentage: Int,
    val carbsPercentage: Int,
    val mealType: MealTypeDomainModel,
    val isConsumed: Boolean
)
