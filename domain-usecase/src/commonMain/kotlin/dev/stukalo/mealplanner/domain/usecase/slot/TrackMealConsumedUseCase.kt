package dev.stukalo.mealplanner.domain.usecase.slot

interface TrackMealConsumedUseCase {
    suspend operator fun invoke(
        slotId: Int,
        calories: Double,
        proteins: Double,
        fats: Double,
        carbohydrates: Double
    ): Result<Unit>
}
