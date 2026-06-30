package dev.stukalo.mealplanner.domain.usecase.nutrition

import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel

interface UpdateDailyProgressUseCase {
    suspend operator fun invoke(progress: DailyProgressDomainModel): Result<Unit>
}
