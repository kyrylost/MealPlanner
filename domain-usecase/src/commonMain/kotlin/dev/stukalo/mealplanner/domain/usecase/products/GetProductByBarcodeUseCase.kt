package dev.stukalo.mealplanner.domain.usecase.products

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel

interface GetProductByBarcodeUseCase {
    suspend operator fun invoke(barcode: String): Result<ProductDomainModel?>
}
