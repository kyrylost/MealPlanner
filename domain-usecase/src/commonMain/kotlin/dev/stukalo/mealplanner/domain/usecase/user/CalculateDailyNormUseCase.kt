package dev.stukalo.mealplanner.domain.usecase.user

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel

/**
 * Use case for calculating the daily nutritional norm based on user profile data.
 */
interface CalculateDailyNormUseCase {
    /**
     * Calculates the daily norm for the given user.
     *
     * @param user The user profile data.
     * @return The calculated daily nutritional norm.
     */
    operator fun invoke(user: UserDomainModel): DailyNormDomainModel
}
