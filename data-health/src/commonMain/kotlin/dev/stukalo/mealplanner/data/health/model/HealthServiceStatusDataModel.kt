package dev.stukalo.mealplanner.data.health.model

/**
 * Data layer representation of health service status.
 */
enum class HealthServiceStatusDataModel {
    /** Service is fully available. */
    AVAILABLE,

    /** Service is not supported on the device. */
    NOT_SUPPORTED,

    /** Service is supported but needs installation. */
    NOT_INSTALLED
}
