package dev.stukalo.mealplanner.core.platform

/**
 * Interface for platform-specific health management functions.
 */
interface HealthManager {
    /**
     * Opens the system health settings for managing permissions and data exchange.
     */
    fun openHealthSettings()

    /**
     * Opens the Play Store to install Health Connect.
     */
    fun installHealthConnect()
}
