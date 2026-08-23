package dev.stukalo.mealplanner.domain.usecase.slot

/**
 * Interface for checking notification permissions.
 */
interface HasNotificationPermissionUseCase {
    /**
     * Returns true if the application has permission to post notifications.
     */
    operator fun invoke(): Boolean
}
