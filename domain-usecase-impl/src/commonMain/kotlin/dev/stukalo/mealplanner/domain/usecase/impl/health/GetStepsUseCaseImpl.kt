package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.GetStepsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetStepsUseCaseImpl(private val healthRepository: HealthRepository) : GetStepsUseCase {
    override fun invoke(date: LocalDate): Flow<Int> = healthRepository.getStepsAsFlow(date)
}
