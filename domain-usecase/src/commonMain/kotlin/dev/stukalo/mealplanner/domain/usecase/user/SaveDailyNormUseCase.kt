package dev.stukalo.mealplanner.domain.usecase.user

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel

interface SaveDailyNormUseCase {
    suspend operator fun invoke(dailyNorm: DailyNormDomainModel): Result<Unit>
}
