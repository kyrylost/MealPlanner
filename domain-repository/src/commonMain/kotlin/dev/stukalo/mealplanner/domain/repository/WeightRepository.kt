package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

/**
 * Repository for managing weight history data.
 */
interface WeightRepository {
    /**
     * Saves a weight entry.
     *
     * @param weight The weight entry to save.
     */
    suspend fun saveWeight(weight: WeightHistoryDomainModel): Result<Unit>

    /**
     * Returns the full weight history as a flow.
     */
    fun getWeightHistoryAsFlow(): Flow<List<WeightHistoryDomainModel>>

    /**
     * Returns the weight history within a period as a flow.
     *
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     */
    fun getWeightHistoryByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<WeightHistoryDomainModel>>
}
