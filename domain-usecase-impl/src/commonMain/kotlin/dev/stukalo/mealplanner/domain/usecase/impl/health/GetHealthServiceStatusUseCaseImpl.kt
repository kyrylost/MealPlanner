package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.GetHealthServiceStatusUseCase

/**
 * Implementation of [GetHealthServiceStatusUseCase].
 *
 * @property healthRepository The health repository.
 */
class GetHealthServiceStatusUseCaseImpl(private val healthRepository: HealthRepository) :
    GetHealthServiceStatusUseCase {
    override suspend fun invoke(): HealthServiceStatus = healthRepository.getStatus()
}
