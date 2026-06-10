package dev.stukalo.mealplanner.domain.usecase

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import kotlinx.coroutines.flow.Flow

interface GetProductsByQueryUseCase {
     suspend operator fun invoke(
        query: String,
    ): Flow<PagingData<ProductDomainModel>>
}
