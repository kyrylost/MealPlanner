package dev.stukalo.mealplanner.domain.usecase.nutrition

import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel

interface UpdateNutrientProgressUseCase {
    suspend operator fun invoke(
        type: NutrientTypeDomainModel,
        amount: Float,
    ): Result<Unit>
}
