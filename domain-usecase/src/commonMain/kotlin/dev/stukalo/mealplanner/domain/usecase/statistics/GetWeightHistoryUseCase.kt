package dev.stukalo.mealplanner.domain.usecase.statistics

import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsPoint
import kotlinx.coroutines.flow.Flow

interface GetWeightHistoryUseCase {
    operator fun invoke(interval: StatisticsInterval): Flow<List<StatisticsPoint>>
}
