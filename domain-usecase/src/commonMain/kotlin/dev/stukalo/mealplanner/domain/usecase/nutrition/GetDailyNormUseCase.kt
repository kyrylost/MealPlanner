package dev.stukalo.mealplanner.domain.usecase.nutrition

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import kotlinx.coroutines.flow.Flow

interface GetDailyNormUseCase {
    operator fun invoke(): Flow<DailyNormDomainModel?>
}
