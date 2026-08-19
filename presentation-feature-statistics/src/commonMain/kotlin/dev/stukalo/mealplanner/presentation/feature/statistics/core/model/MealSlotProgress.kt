package dev.stukalo.mealplanner.presentation.feature.statistics.core.model

/**
 * Represents the progress of a single meal slot.
 */
data class MealSlotProgress(
    val id: Int,
    val name: String,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val isConsumed: Boolean
)
