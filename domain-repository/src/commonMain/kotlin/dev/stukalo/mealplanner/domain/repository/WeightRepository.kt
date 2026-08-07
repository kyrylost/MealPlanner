package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface WeightRepository {
    suspend fun saveWeight(weight: WeightHistoryDomainModel): Result<Unit>
    fun getWeightHistoryAsFlow(): Flow<List<WeightHistoryDomainModel>>
    fun getWeightHistoryByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<WeightHistoryDomainModel>>
}
