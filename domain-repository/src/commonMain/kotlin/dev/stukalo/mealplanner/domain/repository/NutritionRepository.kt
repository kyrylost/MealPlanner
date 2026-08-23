package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

/**
 * Repository for managing daily nutritional norms and progress.
 */
interface NutritionRepository {
    /**
     * Saves the calculated daily nutritional norms for the user.
     *
     * @param dailyNorm The nutritional norms to save.
     */
    suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit>

    /**
     * Saves or updates the daily nutritional progress.
     *
     * @param progress The nutritional progress data.
     */
    suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit>

    /**
     * Returns the current daily nutritional norm as a flow.
     */
    fun getDailyNormAsFlow(): Flow<DailyNormDomainModel?>

    /**
     * Returns the daily nutritional progress for a specific date as a flow.
     *
     * @param date The date to retrieve progress for.
     */
    fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?>

    /**
     * Returns the daily nutritional progress for a specific period as a flow.
     *
     * @param startDate The start date of the period (inclusive).
     * @param endDate The end date of the period (inclusive).
     */
    fun getProgressByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<DailyProgressDomainModel>>
}
