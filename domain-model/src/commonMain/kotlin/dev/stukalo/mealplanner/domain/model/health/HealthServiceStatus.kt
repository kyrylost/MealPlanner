package dev.stukalo.mealplanner.domain.model.health

/**
 * Represents the status of the health service on the device.
 */
enum class HealthServiceStatus {
    /** The health service is available and installed. */
    AVAILABLE,

    /** The health service is not supported on this device/OS version. */
    NOT_SUPPORTED,

    /** The health service is supported but not installed. */
    NOT_INSTALLED
}
