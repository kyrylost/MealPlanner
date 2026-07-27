package dev.stukalo.mealplanner.domain.usecase.recipes

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import kotlinx.coroutines.flow.Flow

interface GetRecommendedRecipesUseCase {
    operator fun invoke(slot: MealSlotDomainModel? = null): Flow<PagingData<RecipeDomainModel>>
}
