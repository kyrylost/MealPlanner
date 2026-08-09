package dev.stukalo.mealplanner.domain.repository

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository for searching products and retrieving product details.
 */
interface SearchRepository {
    /**
     * Retrieves autocomplete hints for the given query.
     *
     * @param query The search query.
     * @param limit The maximum number of hints to return.
     * @return A list of autocomplete hints.
     */
    suspend fun getAutoCompleteHints(query: String, limit: Int): List<String>

    /**
     * Retrieves a paginated list of products matching the given query.
     *
     * @param query The search query.
     * @return A flow of paginated products.
     */
    fun getProductsByQuery(query: String): Flow<PagingData<ProductDomainModel>>

    /**
     * Retrieves a product by its barcode/QR code.
     *
     * @param qrCode The barcode or QR code.
     * @return The product if found, null otherwise.
     */
    suspend fun getProductByQrCode(qrCode: String): ProductDomainModel?

    /**
     * Retrieves product details by its unique identifier.
     *
     * @param id The product ID.
     * @return The product details if found, null otherwise.
     */
    suspend fun getProductById(id: String): ProductDomainModel?
}
