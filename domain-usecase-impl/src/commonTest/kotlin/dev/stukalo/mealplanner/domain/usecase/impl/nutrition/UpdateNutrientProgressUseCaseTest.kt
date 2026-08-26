package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_CARB_GRAM
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

class UpdateNutrientProgressUseCaseTest {

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

    private val useCase = UpdateNutrientProgressUseCaseImpl(
        getProgressUseCase,
        updateProgressUseCase,
        clock
    )

    @Test
    fun `given existing progress when updating carbohydrates then adds calories and grams`() = runTest {
        val date = LocalDate(2026, 8, 26)
        getProgressUseCase.progress = DailyProgressDomainModel(date, 1000.0, 50.0, 30.0, 100.0)

        useCase(NutrientTypeDomainModel.CARBOHYDRATES, 50f)

        val result = updateProgressUseCase.lastUpdatedProgress
        assertEquals(150.0, result?.consumedCarbohydrates)
        val expectedCalories = 1000.0 + (50 * CALORIES_PER_CARB_GRAM)
        assertEquals(expectedCalories, result?.consumedCalories)
    }
}
