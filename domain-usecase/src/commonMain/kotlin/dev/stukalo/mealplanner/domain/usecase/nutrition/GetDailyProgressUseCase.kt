package dev.stukalo.mealplanner.domain.usecase.nutrition

import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface GetDailyProgressUseCase {
    operator fun invoke(date: LocalDate): Flow<DailyProgressDomainModel?>
}
