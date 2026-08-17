package dev.stukalo.mealplanner.core.platform

/**
 * Desktop implementation of [HealthManager].
 */
class DesktopHealthManager : HealthManager {
    override fun openHealthSettings() {
        // Not supported on Desktop.
    }

    override fun installHealthConnect() {
        // Not supported on Desktop.
    }
}
