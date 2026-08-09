package dev.stukalo.mealplanner.domain.usecase.products

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel

/**
 * Use case to fetch product details by either ID or barcode.
 * Uses repository caching to provide instant results if data was previously loaded.
 */
interface GetProductDetailsUseCase {
    suspend operator fun invoke(productId: String? = null, barcode: String? = null): ProductDomainModel?
}
