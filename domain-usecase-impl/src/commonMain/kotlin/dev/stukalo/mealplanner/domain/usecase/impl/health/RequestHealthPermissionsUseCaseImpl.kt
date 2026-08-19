package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.RequestHealthPermissionsUseCase

/**
 * Implementation of [RequestHealthPermissionsUseCase].
 *
 * @property healthRepository The repository for health data and permissions.
 */
class RequestHealthPermissionsUseCaseImpl(private val healthRepository: HealthRepository) :
    RequestHealthPermissionsUseCase {
    override suspend fun invoke(group: HealthPermissionGroup?): Result<Set<HealthPermissionType>> =
        healthRepository.requestPermissions(group)
}
