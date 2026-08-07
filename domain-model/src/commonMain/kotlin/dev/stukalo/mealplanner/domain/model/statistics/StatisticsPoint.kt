package dev.stukalo.mealplanner.domain.model.statistics

import kotlinx.datetime.LocalDate

/**
 * A data point for statistics charts.
 */
data class StatisticsPoint(val date: LocalDate, val value: Double, val target: Double? = null)
