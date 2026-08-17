package dev.stukalo.mealplanner.data.health.model

import kotlinx.datetime.LocalDate

/**
 * Data model for weight record in health services.
 */
data class WeightHealthModel(val date: LocalDate, val weight: Double)
