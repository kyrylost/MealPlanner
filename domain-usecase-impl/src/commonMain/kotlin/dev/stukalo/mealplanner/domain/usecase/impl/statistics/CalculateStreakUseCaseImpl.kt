package dev.stukalo.mealplanner.domain.usecase.impl.statistics

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.usecase.statistics.CalculateStreakUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Implementation of [CalculateStreakUseCase] that calculates the current success streak.
 * A streak is defined as consecutive days where the user's nutrient intake was within
 * a predefined margin of their daily norm.
 *
 * @property nutritionRepository Repository to fetch daily norms and progress history.
 * @property clock Clock provider for date calculations.
 */
class CalculateStreakUseCaseImpl(private val nutritionRepository: NutritionRepository, private val clock: Clock) :
    CalculateStreakUseCase {

    override fun invoke(): Flow<Int> {
        return combine(
            nutritionRepository.getDailyNormAsFlow(),
            nutritionRepository.getProgressByPeriodAsFlow(
                startDate = clock.now().toLocalDateTime(
                    TimeZone.currentSystemDefault()
                ).date.minus(STREAK_LOOKBACK_DAYS, DateTimeUnit.DAY),
                endDate = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            )
        ) { norm, history ->
            if (norm == null) return@combine 0

            calculateStreak(norm, history.sortedByDescending { it.date })
        }
    }

    private fun calculateStreak(norm: DailyNormDomainModel, history: List<DailyProgressDomainModel>): Int {
        var streak = 0
        val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        for (dayProgress in history) {
            if (dayProgress.date == today) {
                if (isSuccessful(dayProgress, norm)) {
                    streak++
                } else if (isOverLimit(dayProgress, norm)) {
                    // Today is failed, streak starts from 0 (if we consider streak up to today)
                    // or we could show streak as of yesterday.
                    // Following the requirement: "current success streak".
                    continue
                }
            } else {
                if (isSuccessful(dayProgress, norm)) {
                    streak++
                } else {
                    break // Streak broken
                }
            }
        }
        return streak
    }

    private fun isSuccessful(progress: DailyProgressDomainModel, norm: DailyNormDomainModel): Boolean =
        isWithinMargin(progress.consumedCalories, norm.calories) &&
            isWithinMargin(progress.consumedProteins, norm.proteins) &&
            isWithinMargin(progress.consumedFats, norm.fats) &&
            isWithinMargin(progress.consumedCarbohydrates, norm.carbohydrates)

    private fun isOverLimit(progress: DailyProgressDomainModel, norm: DailyNormDomainModel): Boolean =
        progress.consumedCalories > norm.calories * SUCCESS_MARGIN_UPPER ||
            progress.consumedProteins > norm.proteins * SUCCESS_MARGIN_UPPER ||
            progress.consumedFats > norm.fats * SUCCESS_MARGIN_UPPER ||
            progress.consumedCarbohydrates > norm.carbohydrates * SUCCESS_MARGIN_UPPER

    private fun isWithinMargin(value: Double, target: Double): Boolean {
        val lowerBound = target * SUCCESS_MARGIN_LOWER
        val upperBound = target * SUCCESS_MARGIN_UPPER
        return value in lowerBound..upperBound
    }

    companion object {
        private const val STREAK_LOOKBACK_DAYS = 365
        private const val SUCCESS_MARGIN_LOWER = 0.95
        private const val SUCCESS_MARGIN_UPPER = 1.05
    }
}
