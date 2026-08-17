package dev.stukalo.mealplanner.data.health.model

import kotlinx.datetime.LocalDate

/**
 * Data model for nutrition record in health services.
 */
data class NutritionHealthModel(
    val date: LocalDate,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
)
