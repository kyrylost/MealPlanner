package dev.stukalo.mealplanner.domain.usecase.impl.products

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.usecase.products.GetProductDetailsUseCase

internal class GetProductDetailsUseCaseImpl(private val searchRepository: SearchRepository) : GetProductDetailsUseCase {
    override suspend fun invoke(productId: String?, barcode: String?): ProductDomainModel? = when {
        productId != null -> searchRepository.getProductById(productId)
        barcode != null -> searchRepository.getProductByQrCode(barcode)
        else -> null
    }
}
