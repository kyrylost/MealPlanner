package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.model.exception.HealthException
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.SyncHealthDataUseCase

/**
 * Implementation of [SyncHealthDataUseCase].
 */
class SyncHealthDataUseCaseImpl(private val healthRepository: HealthRepository) : SyncHealthDataUseCase {
    override suspend fun invoke(): Result<Unit> {
        if (healthRepository.getStatus() != HealthServiceStatus.AVAILABLE) {
            return Result.failure(HealthException.Unavailable())
        }

        return healthRepository.syncAllData()
    }
}
