package dev.stukalo.mealplanner.domain.usecase.impl.nutrition

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UpdateDailyProgressUseCaseTest {

    private class FakeNutritionRepository : NutritionRepository {
        var lastSavedProgress: DailyProgressDomainModel? = null
        override suspend fun saveDailyProgress(progress: DailyProgressDomainModel): Result<Unit> {
            lastSavedProgress = progress
            return Result.success(Unit)
        }

        override fun getDailyNormAsFlow(): Flow<DailyNormDomainModel?> = TODO()
        override fun getDailyProgressAsFlow(date: LocalDate): Flow<DailyProgressDomainModel?> = TODO()
        override fun getProgressByPeriodAsFlow(
            startDate: LocalDate,
            endDate: LocalDate
        ): Flow<List<DailyProgressDomainModel>> = TODO()
        override suspend fun saveDailyNorm(dailyNorm: DailyNormDomainModel): Result<Unit> = TODO()
    }

    private class FakeHealthRepository : HealthRepository {
        var lastWrittenDate: LocalDate? = null
        var lastWrittenProgress: DailyProgressDomainModel? = null

        override suspend fun writeNutrition(date: LocalDate, progress: DailyProgressDomainModel): Result<Unit> {
            lastWrittenDate = date
            lastWrittenProgress = progress
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
        override suspend fun writeWeight(weight: WeightHistoryDomainModel): Result<Unit> = TODO()
    }

    @Test
    fun `invoke saves progress to repository and writes to health`() = runTest {
        val nutritionRepository = FakeNutritionRepository()
        val healthRepository = FakeHealthRepository()
        val useCase = UpdateDailyProgressUseCaseImpl(nutritionRepository, healthRepository)
        val progress = DailyProgressDomainModel(LocalDate(2026, 8, 26), 2000.0, 150.0, 70.0, 200.0)

        val result = useCase(progress)

        assertTrue(result.isSuccess)
        assertEquals(progress, nutritionRepository.lastSavedProgress)
        assertEquals(progress.date, healthRepository.lastWrittenDate)
        assertEquals(progress, healthRepository.lastWrittenProgress)
    }
}
