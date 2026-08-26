package dev.stukalo.mealplanner.domain.usecase.impl.statistics

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Instant

class StatisticsUseCaseTests {

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

    private class FakeWeightRepository : WeightRepository {
        var history = listOf<WeightHistoryDomainModel>()
        var lastSavedWeight: WeightHistoryDomainModel? = null

        override fun getWeightHistoryByPeriodAsFlow(
            startDate: LocalDate,
            endDate: LocalDate
        ): Flow<List<WeightHistoryDomainModel>> = flowOf(history)
        override suspend fun saveWeight(weight: WeightHistoryDomainModel): Result<Unit> {
            lastSavedWeight = weight
            return Result.success(Unit)
        }
        override fun getWeightHistoryAsFlow(): Flow<List<WeightHistoryDomainModel>> = TODO()
    }

    private class FakeHealthRepository : HealthRepository {
        var lastWrittenWeight: WeightHistoryDomainModel? = null
        override suspend fun writeWeight(weight: WeightHistoryDomainModel): Result<Unit> {
            lastWrittenWeight = weight
            return Result.success(Unit)
        }

        override suspend fun isAvailable(): Boolean = TODO()
        override suspend fun getStatus(): HealthServiceStatus = TODO()
        override suspend fun hasPermissions(): Boolean = TODO()
        override suspend fun getPermissionStatuses(): List<HealthPermissionStatus> = TODO()
        override suspend fun requestPermissions(group: HealthPermissionGroup?): Result<Set<HealthPermissionType>> =
            TODO()
        override fun getStepsAsFlow(date: LocalDate): Flow<Int> = TODO()
        override suspend fun syncAllData(): Result<Unit> = TODO()
        override suspend fun writeNutrition(date: LocalDate, progress: DailyProgressDomainModel): Result<Unit> = TODO()
    }

    private class FakeClock(val instant: Instant) : Clock {
        override fun now(): Instant = instant
    }

    private val now = Instant.parse("2026-08-26T12:00:00Z")
    private val clock = FakeClock(now)

    @Test
    fun `GetStatisticsUseCase aggregates weekly data correctly`() = runTest {
        val today = LocalDate(2026, 8, 26)
        val repository = FakeNutritionRepository()
        repository.norm = DailyNormDomainModel(2000.0, 100.0, 50.0, 200.0)
        repository.history = listOf(
            DailyProgressDomainModel(today, 1500.0, 80.0, 40.0, 150.0)
        )
        val useCase = GetStatisticsUseCaseImpl(repository, clock)

        val result = useCase(StatisticsInterval.WEEK, PfcCategory.CALORIES).first()

        assertEquals(7, result.size)
        assertEquals(1500.0, result.last().value)
        assertEquals(2000.0, result.last().target)
        assertEquals(0.0, result.first().value)
    }

    @Test
    fun `SaveWeightUseCase saves weight to repository and health`() = runTest {
        val weightRepository = FakeWeightRepository()
        val healthRepository = FakeHealthRepository()
        val useCase = SaveWeightUseCaseImpl(weightRepository, healthRepository, clock)

        val result = useCase(85.0)

        assertTrue(result.isSuccess)
        assertEquals(85.0, weightRepository.lastSavedWeight?.weight)
        assertEquals(85.0, healthRepository.lastWrittenWeight?.weight)
    }

    @Test
    fun `GetWeightHistoryUseCase returns interpolated weekly weight`() = runTest {
        val weightRepository = FakeWeightRepository()
        weightRepository.history = listOf(
            WeightHistoryDomainModel(LocalDate(2026, 8, 24), 80.0)
        )
        val useCase = GetWeightHistoryUseCaseImpl(weightRepository, clock)

        val result = useCase(StatisticsInterval.WEEK).first()

        assertEquals(7, result.size)
        // From 24th onwards it should be 80.0
        assertEquals(80.0, result.find { it.date == LocalDate(2026, 8, 24) }?.value)
        assertEquals(80.0, result.find { it.date == LocalDate(2026, 8, 26) }?.value)
        // Before 24th it should be 0.0 (as it's before the first entry)
        assertEquals(0.0, result.find { it.date == LocalDate(2026, 8, 23) }?.value)
    }
}
