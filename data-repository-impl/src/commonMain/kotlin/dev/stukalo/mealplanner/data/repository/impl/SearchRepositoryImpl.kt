package dev.stukalo.mealplanner.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.stukalo.mealplanner.data.network.edamam.food.source.EdamamFoodNetSource
import dev.stukalo.mealplanner.data.network.fooddatacentral.source.FoodDataCentralNetSource
import dev.stukalo.mealplanner.data.network.openfoodfacts.source.OpenFoodFactsNetSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.FdcProductMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.OffProductMapper
import dev.stukalo.mealplanner.data.repository.impl.paging.ProductPagingSource
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

internal class SearchRepositoryImpl(
    private val edamamFoodNetSource: EdamamFoodNetSource,
    private val fdcNetSource: FoodDataCentralNetSource,
    private val offNetSource: OpenFoodFactsNetSource,
    private val fdcProductMapper: FdcProductMapper,
    private val offProductMapper: OffProductMapper,
) : SearchRepository {

    override suspend fun getAutoCompleteHints(query: String, limit: Int): List<String> {
        return edamamFoodNetSource.getAutoCompleteHints(query, limit.toString())
    }

    override fun getProductsByQuery(query: String): Flow<PagingData<ProductDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PRODUCTS_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ProductPagingSource(
                    fdcNetSource = fdcNetSource,
                    fdcProductMapper = fdcProductMapper,
                    query = query
                )
            }
        ).flow
    }

    override suspend fun getProductByQrCode(qrCode: String): ProductDomainModel? {
        val response = offNetSource.getProductByBarcode(qrCode)
        return if (response.status == 1) offProductMapper.mapTo(response) else null
    }

    companion object {
        private const val PRODUCTS_PAGE_SIZE = 20
    }
}
