/**
 * Platform-specific manager for locale settings.
 */
package dev.stukalo.mealplanner.presentation.core.platform

/**
 * Sets the application locale.
 *
 * @param locale The locale string (e.g., "en", "uk").
 */
expect fun setLocale(locale: String)

/**
 * Returns the system's current locale.
 */
expect fun getSystemLocale(): String
