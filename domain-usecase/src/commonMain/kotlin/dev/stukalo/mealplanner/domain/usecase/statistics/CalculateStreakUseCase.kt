package dev.stukalo.mealplanner.domain.usecase.statistics

import kotlinx.coroutines.flow.Flow

interface CalculateStreakUseCase {
    operator fun invoke(): Flow<Int>
}
