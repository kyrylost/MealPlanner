package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.GetHealthPermissionStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Implementation of [GetHealthPermissionStatusUseCase].
 *
 * @property healthRepository The health repository.
 */
class GetHealthPermissionStatusUseCaseImpl(private val healthRepository: HealthRepository) :
    GetHealthPermissionStatusUseCase {
    override fun invoke(): Flow<List<HealthPermissionStatus>> = flow {
        emit(healthRepository.getPermissionStatuses())
    }
}
