package dev.stukalo.mealplanner.domain.usecase.impl.products

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.usecase.products.GetProductsByQueryUseCase
import kotlinx.coroutines.flow.Flow

internal class GetProductsByQueryUseCaseImpl(private val searchRepository: SearchRepository) :
    GetProductsByQueryUseCase {
    override suspend operator fun invoke(query: String): Flow<PagingData<ProductDomainModel>> =
        searchRepository.getProductsByQuery(query)
}
