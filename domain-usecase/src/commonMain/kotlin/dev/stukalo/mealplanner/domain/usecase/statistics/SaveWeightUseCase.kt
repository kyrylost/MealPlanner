package dev.stukalo.mealplanner.domain.usecase.statistics

interface SaveWeightUseCase {
    suspend operator fun invoke(weight: Double): Result<Unit>
}
