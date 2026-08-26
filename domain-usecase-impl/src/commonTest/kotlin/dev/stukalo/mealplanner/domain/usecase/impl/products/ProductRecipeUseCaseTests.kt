package dev.stukalo.mealplanner.domain.usecase.impl.products

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.repository.RecipeRepository
import dev.stukalo.mealplanner.domain.repository.SearchRepository
import dev.stukalo.mealplanner.domain.usecase.impl.recipes.GetRecipeByIdUseCaseImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProductRecipeUseCaseTests {

    private class FakeSearchRepository : SearchRepository {
        var barcodeResult: ProductDomainModel? = null
        var idResult: ProductDomainModel? = null
        var hintsResult = listOf<String>()
        var pagingResult = flowOf(PagingData.from(listOf<ProductDomainModel>()))

        override suspend fun getProductByQrCode(qrCode: String): ProductDomainModel? = barcodeResult
        override suspend fun getProductById(id: String): ProductDomainModel? = idResult
        override suspend fun getAutoCompleteHints(query: String, limit: Int): List<String> = hintsResult
        override fun getProductsByQuery(query: String): Flow<PagingData<ProductDomainModel>> = pagingResult
    }

    private class FakeRecipeRepository : RecipeRepository {
        var recipeResult: Result<RecipeDomainModel> = Result.failure(Exception())
        var recipesPagingResult = flowOf(PagingData.from(listOf<RecipeDomainModel>()))

        override suspend fun getRecipeById(id: String): Result<RecipeDomainModel> = recipeResult
        override fun getRecipesByNutrients(
            type: String,
            calories: IntRange,
            carbohydrates: IntRange,
            fats: IntRange,
            proteins: IntRange,
            mealTypes: List<MealTypeDomainModel>,
            query: String?
        ): Flow<PagingData<RecipeDomainModel>> = recipesPagingResult
    }

    @Test
    fun `GetProductByBarcodeUseCase returns product from repository`() = runTest {
        val repository = FakeSearchRepository()
        val product = ProductDomainModel(id = "1", productName = "Apple")
        repository.barcodeResult = product
        val useCase = GetProductByBarcodeUseCaseImpl(repository)

        val result = useCase("12345")
        assertEquals(product, result.getOrNull())
    }

    @Test
    fun `GetRecipeByIdUseCase returns recipe from repository`() = runTest {
        val repository = FakeRecipeRepository()
        val recipe = RecipeDomainModel(id = "1", product = ProductDomainModel(id = "1", productName = "Test"))
        repository.recipeResult = Result.success(recipe)
        val useCase = GetRecipeByIdUseCaseImpl(repository)

        val result = useCase("1")
        assertEquals(recipe, result.getOrNull())
    }

    @Test
    fun `GetProductDetailsUseCase calls correct repository method`() = runTest {
        val repository = FakeSearchRepository()
        val productById = ProductDomainModel(id = "ID", productName = "By ID")
        val productByBarcode = ProductDomainModel(id = "BC", productName = "By Barcode")
        repository.idResult = productById
        repository.barcodeResult = productByBarcode

        val useCase = GetProductDetailsUseCaseImpl(repository)

        assertEquals(productById, useCase(productId = "ID", barcode = null))
        assertEquals(productByBarcode, useCase(productId = null, barcode = "BC"))
        assertNull(useCase(null, null))
    }

    @Test
    fun `GetAutoCompleteHintsUseCase returns hints from repository`() = runTest {
        val repository = FakeSearchRepository()
        repository.hintsResult = listOf("Apple", "Apricot")
        val useCase = GetAutoCompleteHintsUseCaseImpl(repository)

        val result = useCase("Ap")
        assertEquals(repository.hintsResult, result.getOrNull())
    }
}
