package dev.stukalo.mealplanner.platform

import dev.stukalo.mealplanner.domain.service.HealthManager

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
