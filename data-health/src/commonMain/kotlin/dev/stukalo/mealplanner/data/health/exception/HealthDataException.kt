package dev.stukalo.mealplanner.data.health.exception

/**
 * Base class for health-related data exceptions.
 */
sealed class HealthDataException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    /**
     * Health service is unavailable on the device.
     */
    class ServiceUnavailable : HealthDataException()

    /**
     * Health service is not installed on the device.
     */
    class ServiceNotInstalled : HealthDataException()

    /**
     * Required health permissions are missing.
     */
    class InsufficientPermissions : HealthDataException()

    /**
     * Failed to synchronize data with health service.
     */
    class SyncError(cause: Throwable? = null) : HealthDataException(cause = cause)

    /**
     * Generic error while writing data to health service.
     */
    class WriteError(cause: Throwable? = null) : HealthDataException(cause = cause)
}
