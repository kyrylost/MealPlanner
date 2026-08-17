package dev.stukalo.mealplanner.domain.usecase.health

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

/**
 * Use case to retrieve the amount of steps from health service
 */
interface GetStepsUseCase {

    /**
     * Retrieves the amount of steps from health service
     */
    operator fun invoke(date: LocalDate): Flow<Int>
}
