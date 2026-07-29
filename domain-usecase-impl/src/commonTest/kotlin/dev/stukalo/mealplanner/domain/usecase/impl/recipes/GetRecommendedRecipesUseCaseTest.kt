package dev.stukalo.mealplanner.domain.usecase.impl.recipes

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class GetRecommendedRecipesUseCaseTest {

    @Test
    fun `given partially consumed day when getting recommendations then calculates adaptive target calories based on remaining pool`() = runTest {
        // Given
        val slots = listOf(
            MealSlotDomainModel(
                id = 1,
                name = "Breakfast",
                startTime = LocalTime(7, 0),
                proteinsPercentage = 25,
                fatsPercentage = 25,
                carbsPercentage = 25,
                mealTypes = listOf(MealTypeDomainModel.BREAKFAST),
                isConsumed = true,
            ),
            MealSlotDomainModel(
                id = 2,
                name = "Lunch",
                startTime = LocalTime(12, 0),
                proteinsPercentage = 35,
                fatsPercentage = 35,
                carbsPercentage = 35,
                mealTypes = listOf(MealTypeDomainModel.LUNCH),
                isConsumed = false,
            ),
            MealSlotDomainModel(
                id = 3,
                name = "Dinner",
                startTime = LocalTime(19, 0),
                proteinsPercentage = 40,
                fatsPercentage = 40,
                carbsPercentage = 40,
                mealTypes = listOf(MealTypeDomainModel.DINNER),
                isConsumed = false,
            )
        )
        // Breakfast is consumed. Remaining Pool Percentage: 35 (Lunch) + 40 (Dinner) = 75%
        
        val norm = DailyNormDomainModel(
            calories = 2000.0,
            proteins = 100.0,
            fats = 60.0,
            carbohydrates = 265.0,
        )
        val progress = DailyProgressDomainModel(
            date = LocalDate(2024, 1, 1),
            consumedCalories = 500.0,
            consumedProteins = 25.0,
            consumedFats = 15.0,
            consumedCarbohydrates = 66.25,
        )
        // Remaining Calories: 2000 - 500 = 1500
        // Target for Lunch (at 13:00): 1500 * (35 / 75) = 700

        // Mock dependencies
        val getMealScheduleUseCase = object : GetMealScheduleUseCase {
            override fun invoke(): Flow<List<MealSlotDomainModel>> = flowOf(slots)
        }
        val getDailyNormUseCase = object : GetDailyNormUseCase {
            override fun invoke(): Flow<DailyNormDomainModel?> = flowOf(norm)
        }
        val getDailyProgressUseCase = object : GetDailyProgressUseCase {
            override fun invoke(date: LocalDate): Flow<DailyProgressDomainModel?> = flowOf(progress)
        }

        var capturedCalories: IntRange? = null
        val getRecipesUseCase = object : GetRecipesUseCase {
            override fun invoke(
                calories: IntRange,
                carbohydrates: IntRange,
                fats: IntRange,
                proteins: IntRange,
                mealTypes: List<MealTypeDomainModel>,
                query: String?,
            ): Flow<PagingData<RecipeDomainModel>> {
                capturedCalories = calories
                return flowOf(PagingData.empty())
            }
        }

        // Mock clock to 13:00:00 UTC
        val mockClock = object : Clock {
            override fun now(): Instant = Instant.parse("2024-01-01T13:00:00Z")
        }

        val useCase = GetRecommendedRecipesUseCaseImpl(
            getMealScheduleUseCase,
            getDailyNormUseCase,
            getDailyProgressUseCase,
            getRecipesUseCase,
            mockClock,
        )

        // When
        useCase().first()

        // Then
        // Target is 700, Tolerance is 100 -> 600..800
        assertEquals(600..800, capturedCalories)
    }

    @Test
    fun `given no meals consumed when getting recommendations for first slot then calculates target based on total norm`() = runTest {
        // Given
        val slots = listOf(
            MealSlotDomainModel(1, "Breakfast", LocalTime(7, 0), 25, 25, 25, listOf(MealTypeDomainModel.BREAKFAST), false),
            MealSlotDomainModel(2, "Lunch", LocalTime(12, 0), 35, 35, 35, listOf(MealTypeDomainModel.LUNCH), false),
            MealSlotDomainModel(3, "Dinner", LocalTime(19, 0), 40, 40, 40, listOf(MealTypeDomainModel.DINNER), false)
        )
        val norm = DailyNormDomainModel(2000.0, 100.0, 60.0, 265.0)
        val progress = DailyProgressDomainModel(LocalDate(2024, 1, 1), 0.0, 0.0, 0.0, 0.0)
        
        // Target for Breakfast: 2000 * (25 / 100) = 500

        val getMealScheduleUseCase = object : GetMealScheduleUseCase {
            override fun invoke(): Flow<List<MealSlotDomainModel>> = flowOf(slots)
        }
        val getDailyNormUseCase = object : GetDailyNormUseCase {
            override fun invoke(): Flow<DailyNormDomainModel?> = flowOf(norm)
        }
        val getDailyProgressUseCase = object : GetDailyProgressUseCase {
            override fun invoke(date: LocalDate): Flow<DailyProgressDomainModel?> = flowOf(progress)
        }

        var capturedCalories: IntRange? = null
        val getRecipesUseCase = object : GetRecipesUseCase {
            override fun invoke(
                calories: IntRange,
                carbohydrates: IntRange,
                fats: IntRange,
                proteins: IntRange,
                mealTypes: List<MealTypeDomainModel>,
                query: String?,
            ): Flow<PagingData<RecipeDomainModel>> {
                capturedCalories = calories
                return flowOf(PagingData.empty())
            }
        }

        // Mock clock to 08:00:00 UTC (Breakfast time)
        val mockClock = object : Clock {
            override fun now(): Instant = Instant.parse("2024-01-01T08:00:00Z")
        }

        val useCase = GetRecommendedRecipesUseCaseImpl(
            getMealScheduleUseCase,
            getDailyNormUseCase,
            getDailyProgressUseCase,
            getRecipesUseCase,
            mockClock,
        )

        // When
        useCase().first()

        // Then
        // Target is 500, Tolerance is 100 -> 400..600
        assertEquals(400..600, capturedCalories)
    }
}