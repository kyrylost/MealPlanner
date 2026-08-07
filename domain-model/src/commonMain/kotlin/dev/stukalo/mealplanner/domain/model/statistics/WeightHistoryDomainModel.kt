package dev.stukalo.mealplanner.domain.model.statistics

import kotlinx.datetime.LocalDate

data class WeightHistoryDomainModel(val date: LocalDate, val weight: Double)
