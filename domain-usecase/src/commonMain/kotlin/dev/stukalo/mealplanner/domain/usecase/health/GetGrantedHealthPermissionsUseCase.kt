package dev.stukalo.mealplanner.domain.usecase.health

/**
 * Use case to get the set of health permissions currently granted by the system.
 */
interface GetGrantedHealthPermissionsUseCase {
    /**
     * Executes the use case.
     * @return A set of granted permission strings.
     */
    suspend operator fun invoke(): Set<String>
}
