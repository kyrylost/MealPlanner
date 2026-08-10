package dev.stukalo.mealplanner.domain.model.setting

/**
 * Domain model representing the possible theme modes for the application.
 */
enum class ThemeModeDomainModel {
    /** Theme mode follows the system settings. */
    AUTO,

    /** Force light theme mode. */
    LIGHT,

    /** Force dark theme mode. */
    DARK
}
