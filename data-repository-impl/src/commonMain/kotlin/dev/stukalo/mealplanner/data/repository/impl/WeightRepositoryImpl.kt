package dev.stukalo.mealplanner.data.repository.impl

import dev.stukalo.mealplanner.data.database.source.statistics.WeightHistoryDatabaseSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.WeightHistoryMapper
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import dev.stukalo.mealplanner.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

/**
 * Implementation of [WeightRepository].
 *
 * @property weightHistoryDatabaseSource Data source for weight history.
 * @property weightHistoryMapper Mapper for weight history models.
 */
internal class WeightRepositoryImpl(
    private val weightHistoryDatabaseSource: WeightHistoryDatabaseSource,
    private val weightHistoryMapper: WeightHistoryMapper
) : WeightRepository {
    override suspend fun saveWeight(weight: WeightHistoryDomainModel): Result<Unit> =
        weightHistoryDatabaseSource.insert(weightHistoryMapper.mapFrom(weight))

    override fun getWeightHistoryAsFlow(): Flow<List<WeightHistoryDomainModel>> =
        weightHistoryDatabaseSource.getAllAsFlow().map { list ->
            weightHistoryMapper.mapListTo(list)
        }

    override fun getWeightHistoryByPeriodAsFlow(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<WeightHistoryDomainModel>> =
        weightHistoryDatabaseSource.getByPeriodAsFlow(startDate, endDate).map { list ->
            weightHistoryMapper.mapListTo(list)
        }
}
