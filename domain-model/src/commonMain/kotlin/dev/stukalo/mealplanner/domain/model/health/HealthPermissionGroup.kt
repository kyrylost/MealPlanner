package dev.stukalo.mealplanner.domain.model.health

/**
 * Enumeration of health permission groups supported across platforms.
 */
enum class HealthPermissionGroup {
    /** Step count tracking. */
    STEPS,

    /** Reading weight data. */
    WEIGHT_READ,

    /** Writing weight data. */
    WEIGHT_WRITE,

    /** Reading nutrition data. */
    NUTRITION_READ,

    /** Writing nutrition data. */
    NUTRITION_WRITE,

    /** Unified integration (e.g., HealthKit on iOS). */
    INTEGRATED
}
