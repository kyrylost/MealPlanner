package dev.stukalo.mealplanner.core.platform

/**
 * Interface for platform-specific locale management functions.
 */
interface LocaleManager {
    /**
     * Sets the application locale.
     *
     * @param locale The locale string (e.g., "en", "uk").
     */
    fun setLocale(locale: String)

    /**
     * Returns the system's current locale.
     */
    fun getSystemLocale(): String
}
