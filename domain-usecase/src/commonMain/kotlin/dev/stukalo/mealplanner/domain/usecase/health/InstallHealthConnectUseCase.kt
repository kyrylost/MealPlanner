package dev.stukalo.mealplanner.domain.usecase.health

/**
 * Interface for triggering Health Connect installation.
 */
interface InstallHealthConnectUseCase {
    /**
     * Opens the platform-specific store or setup screen to install Health Connect.
     */
    operator fun invoke()
}
