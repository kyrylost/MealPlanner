package dev.stukalo.mealplanner.data.repository.impl

import dev.stukalo.mealplanner.data.database.source.norm.DailyNormDatabaseSource
import dev.stukalo.mealplanner.data.database.source.progress.DailyProgressDatabaseSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.DailyNormMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.DailyProgressMapper
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

internal class NutritionRepositoryImpl(
    private val dailyNormDatabaseSource: DailyNormDatabaseSource,
    private val dailyProgressDatabaseSource: DailyProgressDatabaseSource,
    private val dailyNormMapper: DailyNormMapper,
    private val dailyProgressMapper: DailyProgressMapper,
) : NutritionRepository {

    override suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit> {
        return dailyNormDatabaseSource.insert(dailyNormMapper.mapFrom(dailyNorm))
    }

    override suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit> {
        return dailyProgressDatabaseSource.insert(dailyProgressMapper.mapFrom(progress))
    }

    override fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?> {
        return dailyProgressDatabaseSource.getProgressByDateAsFlow(date).map {
            it?.let { dailyProgressMapper.mapTo(it) }
        }
    }

    override fun getProgressByPeriodAsFlow(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<DailyProgressDomainModel>> {
        return dailyProgressDatabaseSource.getProgressByPeriodAsFlow(startDate, endDate).map { list ->
            dailyProgressMapper.mapListTo(list)
        }
    }
}
