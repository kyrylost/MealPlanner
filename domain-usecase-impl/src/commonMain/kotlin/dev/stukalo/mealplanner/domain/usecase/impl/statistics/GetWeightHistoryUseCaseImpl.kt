package dev.stukalo.mealplanner.domain.usecase.impl.statistics

import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsPoint
import dev.stukalo.mealplanner.domain.repository.WeightRepository
import dev.stukalo.mealplanner.domain.usecase.statistics.GetWeightHistoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Implementation of [GetWeightHistoryUseCase] that fetches and aggregates weight history.
 *
 * @property weightRepository Repository to fetch weight data.
 * @property clock Clock provider for relative date calculations.
 */
class GetWeightHistoryUseCaseImpl(private val weightRepository: WeightRepository, private val clock: Clock) :
    GetWeightHistoryUseCase {
    override fun invoke(interval: StatisticsInterval): Flow<List<StatisticsPoint>> {
        val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startDate = when (interval) {
            StatisticsInterval.WEEK -> today.minus(DAYS_IN_WEEK - 1, DateTimeUnit.DAY)
            StatisticsInterval.MONTH -> today.minus(DAYS_IN_MONTH - 1, DateTimeUnit.DAY)
            StatisticsInterval.YEAR -> today.minus(DAYS_IN_YEAR - 1, DateTimeUnit.DAY)
        }

        return weightRepository.getWeightHistoryByPeriodAsFlow(startDate, today).map { history ->
            val weightMap = history.associateBy { it.date }
            val firstEntryDate = history.minByOrNull { it.date }?.date

            when (interval) {
                StatisticsInterval.WEEK, StatisticsInterval.MONTH -> {
                    val days = if (interval == StatisticsInterval.WEEK) DAYS_IN_WEEK else DAYS_IN_MONTH
                    var lastKnownWeight = 0.0

                    (0 until days).map { i ->
                        val date = today.minus(i, DateTimeUnit.DAY)
                        val entry = weightMap[date]

                        if (entry != null) {
                            lastKnownWeight = entry.weight
                        } else if (firstEntryDate == null || date < firstEntryDate) {
                            // Reset weight if we are before the first actual measurement
                            lastKnownWeight = 0.0
                        }

                        StatisticsPoint(date = date, value = lastKnownWeight)
                    }.reversed()
                }
                StatisticsInterval.YEAR -> {
                    // For year, return 12 points (last 12 months)
                    val monthlyHistory = history.groupBy { it.date.year to it.date.month }
                    var lastKnownWeight = 0.0

                    (0 until MONTHS_IN_YEAR).map { i ->
                        val targetDate = today.minus(i, DateTimeUnit.MONTH)
                        val entries = monthlyHistory[targetDate.year to targetDate.month]
                        val lastEntry = entries?.maxBy { it.date }

                        if (lastEntry != null) {
                            lastKnownWeight = lastEntry.weight
                        } else if (firstEntryDate == null || targetDate < firstEntryDate) {
                            lastKnownWeight = 0.0
                        }

                        StatisticsPoint(
                            date = targetDate,
                            value = lastKnownWeight
                        )
                    }.reversed()
                }
            }
        }
    }

    companion object {
        private const val DAYS_IN_WEEK = 7
        private const val DAYS_IN_MONTH = 30
        private const val DAYS_IN_YEAR = 365
        private const val MONTHS_IN_YEAR = 12
    }
}
