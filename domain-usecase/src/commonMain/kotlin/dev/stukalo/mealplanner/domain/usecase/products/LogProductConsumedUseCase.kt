package dev.stukalo.mealplanner.domain.usecase.products

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel

/**
 * Use case to log a product as consumed by the user.
 */
interface LogProductConsumedUseCase {
    /**
     * Logs the specified [product] with a given [weight] in grams.
     *
     * @param product The product to log.
     * @param weight The weight of the consumed product in grams.
     * @return [Result] indicating success or failure.
     */
    suspend operator fun invoke(product: ProductDomainModel, weight: Float? = null): Result<Unit>
}
