package dev.stukalo.mealplanner.domain.usecase.setting

/**
 * Interface for retrieving the system's current locale.
 */
interface GetSystemLocaleUseCase {
    /**
     * Returns the system's default locale code.
     */
    operator fun invoke(): String
}
