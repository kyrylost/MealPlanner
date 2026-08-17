package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.IsHealthServiceAvailableUseCase

/**
 * Implementation of [IsHealthServiceAvailableUseCase].
 *
 * @property healthRepository The health repository.
 */
class IsHealthServiceAvailableUseCaseImpl(private val healthRepository: HealthRepository) :
    IsHealthServiceAvailableUseCase {
    override suspend fun invoke(): Boolean = healthRepository.isAvailable()
}
