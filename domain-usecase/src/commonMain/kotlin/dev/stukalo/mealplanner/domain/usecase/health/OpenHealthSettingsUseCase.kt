package dev.stukalo.mealplanner.domain.usecase.health

/**
 * Interface for opening the system health settings.
 */
interface OpenHealthSettingsUseCase {
    /**
     * Opens the platform-specific health settings screen.
     */
    operator fun invoke()
}
