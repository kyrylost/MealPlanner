package dev.stukalo.mealplanner.domain.repository

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getAutoCompleteHints(
        query: String,
        limit: Int
    ): List<String>

    fun getProductsByQuery(query: String): Flow<PagingData<ProductDomainModel>>

    suspend fun getProductByQrCode(qrCode: String): ProductDomainModel?
}
