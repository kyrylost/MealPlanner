package dev.stukalo.mealplanner.domain.usecase.impl.statistics

import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class CalculateStreakUseCaseTest {

    private class FakeNutritionRepository : NutritionRepository {
        var norm: DailyNormDomainModel? = null
        var history = listOf<DailyProgressDomainModel>()

        override fun getDailyNormAsFlow(): Flow<DailyNormDomainModel?> = flowOf(norm)
        override fun getProgressByPeriodAsFlow(
            startDate: LocalDate,
            endDate: LocalDate
        ): Flow<List<DailyProgressDomainModel>> = flowOf(history)

        override fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?> = TODO()
        override suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit> = TODO()
        override suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit> = TODO()
    }

    private class FakeClock(val instant: Instant) : Clock {
        override fun now(): Instant = instant
    }

    private val now = Instant.parse("2026-08-26T12:00:00Z")
    private val clock = FakeClock(now)
    private val repository = FakeNutritionRepository()
    private val useCase = CalculateStreakUseCaseImpl(repository, clock)

    @Test
    fun `calculate streak with consecutive successful days`() = runTest {
        val today = LocalDate(2026, 8, 26)
        repository.norm = DailyNormDomainModel(2000.0, 100.0, 50.0, 200.0)
        repository.history = listOf(
            DailyProgressDomainModel(today, 2000.0, 100.0, 50.0, 200.0),
            DailyProgressDomainModel(LocalDate(2026, 8, 25), 1950.0, 98.0, 48.0, 190.0),
            DailyProgressDomainModel(LocalDate(2026, 8, 24), 2050.0, 102.0, 52.0, 210.0)
        )

        val streak = useCase().first()
        assertEquals(3, streak)
    }

    @Test
    fun `calculate streak broken by failed day`() = runTest {
        val today = LocalDate(2026, 8, 26)
        repository.norm = DailyNormDomainModel(2000.0, 100.0, 50.0, 200.0)
        repository.history = listOf(
            DailyProgressDomainModel(today, 2000.0, 100.0, 50.0, 200.0),
            DailyProgressDomainModel(LocalDate(2026, 8, 25), 3000.0, 150.0, 80.0, 300.0), // Failed
            DailyProgressDomainModel(LocalDate(2026, 8, 24), 2000.0, 100.0, 50.0, 200.0)
        )

        val streak = useCase().first()
        assertEquals(1, streak) // Only today is successful
    }
}
