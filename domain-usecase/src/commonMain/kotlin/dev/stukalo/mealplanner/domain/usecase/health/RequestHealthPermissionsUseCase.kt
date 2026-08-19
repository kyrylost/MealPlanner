package dev.stukalo.mealplanner.domain.usecase.health

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType

/**
 * Use case for requesting health permissions from the platform.
 */
interface RequestHealthPermissionsUseCase {
    /**
     * Triggers the platform-specific health permission request dialog.
     *
     * @param group Optional identifier for a specific permission group. If null, requests all.
     * @return A [Result] containing a set of newly granted permission types.
     */
    suspend operator fun invoke(group: HealthPermissionGroup? = null): Result<Set<HealthPermissionType>>
}
