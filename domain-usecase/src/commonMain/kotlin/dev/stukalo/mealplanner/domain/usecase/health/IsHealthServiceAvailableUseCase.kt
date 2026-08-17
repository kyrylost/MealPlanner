package dev.stukalo.mealplanner.domain.usecase.health

/**
 * Use case to check if health service is operational on the current device.
 * Use this for simple binary checks where the specific reason for unavailability
 * is not important (e.g., before starting background data sync).
 */
interface IsHealthServiceAvailableUseCase {

    /**
     * Executes the use case.
     * @return True if data can be read/written, false otherwise.
     */
    suspend operator fun invoke(): Boolean
}
