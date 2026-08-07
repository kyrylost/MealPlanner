package dev.stukalo.mealplanner.domain.usecase.statistics

import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsPoint
import kotlinx.coroutines.flow.Flow

interface GetStatisticsUseCase {
    operator fun invoke(interval: StatisticsInterval, category: PfcCategory): Flow<List<StatisticsPoint>>
}
