package dev.stukalo.mealplanner.domain.usecase

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import kotlinx.coroutines.flow.Flow

interface GetRecipesUseCase {
    operator fun invoke(
        calories: IntRange,
        carbohydrates: IntRange,
        fats: IntRange,
        proteins: IntRange,
        mealType: MealTypeDomainModel,
    ): Flow<PagingData<RecipeDomainModel>>
}
