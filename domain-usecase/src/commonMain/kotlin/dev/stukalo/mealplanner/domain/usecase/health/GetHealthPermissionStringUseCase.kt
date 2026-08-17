package dev.stukalo.mealplanner.domain.usecase.health

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType

/**
 * Use case to map a granular health permission type to a platform-specific permission string.
 */
interface GetHealthPermissionStringUseCase {
    /**
     * Executes the use case.
     * @param type The granular permission type.
     * @return The platform-specific permission identifier.
     */
    operator fun invoke(type: HealthPermissionType): String
}
