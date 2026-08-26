package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class LogProductConsumedUseCaseTest {

    private class FakeGetDailyProgressUseCase : GetDailyProgressUseCase {
        var progress: DailyProgressDomainModel? = null
        override fun invoke(date: LocalDate): Flow<DailyProgressDomainModel?> = flowOf(progress)
    }

    private class FakeUpdateDailyProgressUseCase : UpdateDailyProgressUseCase {
        var lastUpdatedProgress: DailyProgressDomainModel? = null
        override suspend fun invoke(progress: DailyProgressDomainModel): Result<Unit> {
            lastUpdatedProgress = progress
            return Result.success(Unit)
        }
    }

    private class FakeClock(val instant: Instant) : Clock {
        override fun now(): Instant = instant
    }

    private val getProgressUseCase = FakeGetDailyProgressUseCase()
    private val updateProgressUseCase = FakeUpdateDailyProgressUseCase()
    private val clock = FakeClock(Instant.parse("2026-08-26T12:00:00Z"))

    private val useCase = LogProductConsumedUseCaseImpl(
        getProgressUseCase,
        updateProgressUseCase,
        clock
    )

    @Test
    fun `given no progress when logging product by weight then creates new progress and updates`() = runTest {
        val product = ProductDomainModel(
            id = "1",
            productName = "Apple",
            calories = 50f, // per 100g
            nutrients = listOf(
                NutrientDomainModel(NutrientTypeDomainModel.CARBOHYDRATES, 10f),
                NutrientDomainModel(NutrientTypeDomainModel.PROTEIN, 0.5f),
                NutrientDomainModel(NutrientTypeDomainModel.FATS, 0.2f)
            )
        )

        // Log 200g of apple
        useCase(product, 200f)

        val result = updateProgressUseCase.lastUpdatedProgress
        assertEquals(100.0, result?.consumedCalories) // 50 * 2
        assertEquals(20.0, result?.consumedCarbohydrates) // 10 * 2
        assertEquals(1.0, result?.consumedProteins) // 0.5 * 2
        assertEquals(0.4, result?.consumedFats ?: 0.0, 0.0001) // 0.2 * 2
    }

    @Test
    fun `given existing progress when logging product without weight then adds to existing totals`() = runTest {
        val date = LocalDate(2026, 8, 26)
        getProgressUseCase.progress = DailyProgressDomainModel(date, 500.0, 30.0, 20.0, 50.0)

        val product = ProductDomainModel(
            id = "2",
            productName = "Sandwich",
            caloriesTotal = 400f,
            nutrientsTotal = listOf(
                NutrientDomainModel(NutrientTypeDomainModel.CARBOHYDRATES, 40f),
                NutrientDomainModel(NutrientTypeDomainModel.PROTEIN, 15f),
                NutrientDomainModel(NutrientTypeDomainModel.FATS, 10f)
            )
        )

        useCase(product, null)

        val result = updateProgressUseCase.lastUpdatedProgress
        assertEquals(900.0, result?.consumedCalories)
        assertEquals(90.0, result?.consumedCarbohydrates)
        assertEquals(45.0, result?.consumedProteins)
        assertEquals(30.0, result?.consumedFats)
    }

    @Test
    fun `LogRecipeConsumedUseCase logs recipe product`() = runTest {
        val product = ProductDomainModel(id = "3", productName = "Recipe Product")
        val recipe = dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel(
            id = "R1",
            product = product
        )

        val fakeLogProductUseCase = object : dev.stukalo.mealplanner.domain.usecase.products.LogProductConsumedUseCase {
            var lastLoggedProduct: ProductDomainModel? = null
            override suspend fun invoke(product: ProductDomainModel, weight: Float?): Result<Unit> {
                lastLoggedProduct = product
                return Result.success(Unit)
            }
        }

        val logRecipeUseCase = LogRecipeConsumedUseCaseImpl(fakeLogProductUseCase)
        logRecipeUseCase(recipe, 300f)

        assertEquals(product, fakeLogProductUseCase.lastLoggedProduct)
    }
}
