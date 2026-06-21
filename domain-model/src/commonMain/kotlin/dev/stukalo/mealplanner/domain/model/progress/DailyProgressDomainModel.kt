package dev.stukalo.mealplanner.domain.model.progress

import kotlinx.datetime.LocalDate

data class DailyProgressDomainModel(
    val date: LocalDate,
    val consumedCalories: Double,
    val consumedProteins: Double,
    val consumedFats: Double,
    val consumedCarbohydrates: Double
)
