package dev.stukalo.mealplanner.domain.model.exception

import dev.stukalo.mealplanner.core.common.exception.AppException

/**
 * Exceptions related to health tracking and synchronization.
 *
 * @param message The detail message for this exception.
 * @param cause The cause of this exception.
 */
sealed class HealthException(message: String? = null, cause: Throwable? = null) : AppException(message, cause) {
    /**
     * Health tracking is not available on this device.
     */
    class Unavailable : HealthException()

    /**
     * Health service is not installed on this device.
     */
    class NotInstalled : HealthException()

    /**
     * Required health permissions are not granted.
     */
    class PermissionsDenied : HealthException()

    /**
     * An error occurred during synchronization.
     */
    class SyncFailed(cause: Throwable? = null) : HealthException(cause = cause)
}
