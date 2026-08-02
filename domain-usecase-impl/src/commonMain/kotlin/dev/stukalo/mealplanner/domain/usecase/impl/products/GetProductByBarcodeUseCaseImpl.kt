package dev.stukalo.mealplanner.domain.usecase.impl.products

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.usecase.products.GetProductByBarcodeUseCase

internal class GetProductByBarcodeUseCaseImpl(private val searchRepository: SearchRepository) :
    GetProductByBarcodeUseCase {
    override suspend operator fun invoke(barcode: String): Result<ProductDomainModel?> = runCatching {
        searchRepository.getProductByQrCode(barcode)
    }
}
