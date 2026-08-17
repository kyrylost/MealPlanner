package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.GetGrantedHealthPermissionsUseCase

/**
 * Implementation of [GetGrantedHealthPermissionsUseCase].
 *
 * @property healthRepository The health repository.
 */
class GetGrantedHealthPermissionsUseCaseImpl(private val healthRepository: HealthRepository) :
    GetGrantedHealthPermissionsUseCase {
    override suspend fun invoke(): Set<String> = healthRepository.getGrantedPermissions()
}
