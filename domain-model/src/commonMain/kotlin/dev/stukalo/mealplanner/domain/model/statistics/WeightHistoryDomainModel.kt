package dev.stukalo.mealplanner.domain.model.statistics

import kotlinx.datetime.LocalDate

/**
 * Domain model for weight history entry.
 */
data class WeightHistoryDomainModel(val date: LocalDate, val weight: Double)
