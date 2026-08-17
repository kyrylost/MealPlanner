package dev.stukalo.mealplanner.domain.model.user

/**
 * Constants related to user physical parameters.
 */
object UserConstants {
    /** Minimum allowed weight in kilograms. */
    const val MIN_WEIGHT = 30.0

    /** Maximum allowed weight in kilograms. */
    const val MAX_WEIGHT = 300.0

    /** Default weight in kilograms. */
    const val DEFAULT_WEIGHT = 70.0

    /** Minimum allowed height in centimeters. */
    const val MIN_HEIGHT = 100.0

    /** Maximum allowed height in centimeters. */
    const val MAX_HEIGHT = 250.0

    /** Default height in centimeters. */
    const val DEFAULT_HEIGHT = 170.0

    /** Minimum allowed steps target. */
    const val MIN_STEPS_TARGET = 1000

    /** Maximum allowed steps target. */
    const val MAX_STEPS_TARGET = 100000

    /** Default steps target. */
    const val DEFAULT_STEPS_TARGET = 10000

    /** Steps increment for picker. */
    const val STEPS_INCREMENT = 500
}
