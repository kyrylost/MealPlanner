package dev.stukalo.mealplanner.domain.usecase.impl.statistics

import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsPoint
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.statistics.GetStatisticsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Implementation of [GetStatisticsUseCase] that aggregates nutrition data for a given interval.
 *
 * @property nutritionRepository Repository to fetch daily progress and norms.
 * @property clock Clock provider for relative date calculations.
 */
class GetStatisticsUseCaseImpl(private val nutritionRepository: NutritionRepository, private val clock: Clock) :
    GetStatisticsUseCase {
    override fun invoke(interval: StatisticsInterval, category: PfcCategory): Flow<List<StatisticsPoint>> {
        val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startDate = when (interval) {
            StatisticsInterval.WEEK -> today.minus(DAYS_IN_WEEK - 1, DateTimeUnit.DAY)
            StatisticsInterval.MONTH -> today.minus(DAYS_IN_MONTH - 1, DateTimeUnit.DAY)
            StatisticsInterval.YEAR -> today.minus(DAYS_IN_YEAR - 1, DateTimeUnit.DAY)
        }

        return combine(
            nutritionRepository.getProgressByPeriodAsFlow(startDate, today),
            nutritionRepository.getDailyNormAsFlow()
        ) { progress, norm ->
            val dailyProgress = progress.associateBy { it.date }

            when (interval) {
                StatisticsInterval.WEEK, StatisticsInterval.MONTH -> {
                    val days = if (interval == StatisticsInterval.WEEK) DAYS_IN_WEEK else DAYS_IN_MONTH
                    (0 until days).map { i ->
                        val date = today.minus(i, DateTimeUnit.DAY)
                        val day = dailyProgress[date]
                        StatisticsPoint(
                            date = date,
                            value = day?.let {
                                when (category) {
                                    PfcCategory.CALORIES -> it.consumedCalories
                                    PfcCategory.PROTEINS -> it.consumedProteins
                                    PfcCategory.FATS -> it.consumedFats
                                    PfcCategory.CARBOHYDRATES -> it.consumedCarbohydrates
                                }
                            } ?: 0.0,
                            target = norm?.let {
                                when (category) {
                                    PfcCategory.CALORIES -> it.calories
                                    PfcCategory.PROTEINS -> it.proteins
                                    PfcCategory.FATS -> it.fats
                                    PfcCategory.CARBOHYDRATES -> it.carbohydrates
                                }
                            }
                        )
                    }.reversed()
                }
                StatisticsInterval.YEAR -> {
                    // For year, return 12 points (last 12 months)
                    // Each point is the average daily consumption for that month.
                    val monthlyProgress = progress.groupBy { it.date.year to it.date.month }

                    (0 until MONTHS_IN_YEAR).map { i ->
                        val targetDate = today.minus(i, DateTimeUnit.MONTH)
                        val entries = monthlyProgress[targetDate.year to targetDate.month]

                        StatisticsPoint(
                            date = targetDate,
                            value = entries?.map {
                                when (category) {
                                    PfcCategory.CALORIES -> it.consumedCalories
                                    PfcCategory.PROTEINS -> it.consumedProteins
                                    PfcCategory.FATS -> it.consumedFats
                                    PfcCategory.CARBOHYDRATES -> it.consumedCarbohydrates
                                }
                            }?.average() ?: 0.0,
                            target = norm?.let {
                                when (category) {
                                    PfcCategory.CALORIES -> it.calories
                                    PfcCategory.PROTEINS -> it.proteins
                                    PfcCategory.FATS -> it.fats
                                    PfcCategory.CARBOHYDRATES -> it.carbohydrates
                                }
                            }
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
