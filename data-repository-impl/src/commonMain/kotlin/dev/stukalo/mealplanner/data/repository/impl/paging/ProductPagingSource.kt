package dev.stukalo.mealplanner.data.repository.impl.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.stukalo.mealplanner.data.network.fooddatacentral.source.FoodDataCentralNetSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.FdcProductMapper
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel

internal class ProductPagingSource(
    private val fdcNetSource: FoodDataCentralNetSource,
    private val fdcProductMapper: FdcProductMapper,
    private val query: String
) : PagingSource<Int, ProductDomainModel>() {
    override fun getRefreshKey(state: PagingState<Int, ProductDomainModel>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDomainModel> {
        val page = params.key ?: 1
        return try {
            val response =
                fdcNetSource.searchProduct(
                    query = query,
                    pageSize = params.loadSize,
                    pageNumber = page
                )
            val products = response.foods?.map { fdcProductMapper.mapTo(it) } ?: emptyList()

            LoadResult.Page(
                data = products,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (products.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
