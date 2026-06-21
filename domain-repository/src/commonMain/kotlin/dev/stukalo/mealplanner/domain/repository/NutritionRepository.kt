package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface NutritionRepository {
    suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit>
    suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit>
    fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?>
    fun getProgressByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<DailyProgressDomainModel>>
}
