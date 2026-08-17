package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.usecase.health.GetHealthPermissionStringUseCase

/**
 * Implementation of [GetHealthPermissionStringUseCase].
 *
 * @property healthRepository The health repository.
 */
class GetHealthPermissionStringUseCaseImpl(private val healthRepository: HealthRepository) :
    GetHealthPermissionStringUseCase {
    override fun invoke(type: HealthPermissionType): String = healthRepository.getPermissionString(type)
}
