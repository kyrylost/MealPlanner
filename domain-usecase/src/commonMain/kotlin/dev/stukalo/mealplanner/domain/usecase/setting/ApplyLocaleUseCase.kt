package dev.stukalo.mealplanner.domain.usecase.setting

/**
 * Interface for applying a locale to the system.
 */
interface ApplyLocaleUseCase {
    /**
     * Applies the specified locale to the platform's runtime.
     *
     * @param locale The locale code (e.g., "en", "uk").
     */
    operator fun invoke(locale: String)
}
