package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.service.HealthManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HealthUseCaseTests {

    private class FakeHealthRepository : HealthRepository {
        var status: HealthServiceStatus = HealthServiceStatus.AVAILABLE
        var permissionStatuses = listOf<HealthPermissionStatus>()
        var steps = 0
        var syncResult = Result.success(Unit)
        var requestedPermissionsResult = Result.success(emptySet<HealthPermissionType>())

        override suspend fun getStatus(): HealthServiceStatus = status
        override suspend fun getPermissionStatuses(): List<HealthPermissionStatus> = permissionStatuses
        override fun getStepsAsFlow(date: LocalDate): Flow<Int> = flowOf(steps)
        override suspend fun syncAllData(): Result<Unit> = syncResult
        override suspend fun requestPermissions(group: HealthPermissionGroup?): Result<Set<HealthPermissionType>> =
            requestedPermissionsResult

        override suspend fun isAvailable(): Boolean = TODO()
        override suspend fun hasPermissions(): Boolean = TODO()
        override suspend fun writeWeight(weight: WeightHistoryDomainModel): Result<Unit> = TODO()
        override suspend fun writeNutrition(date: LocalDate, progress: DailyProgressDomainModel): Result<Unit> = TODO()
    }

    private class FakeHealthManager : HealthManager {
        var openSettingsCalled = false
        var installHealthCalled = false
        override fun openHealthSettings() {
            openSettingsCalled = true
        }
        override fun installHealthConnect() {
            installHealthCalled = true
        }
    }

    @Test
    fun `GetHealthPermissionStatusUseCase returns statuses from repository`() = runTest {
        val repository = FakeHealthRepository()
        val expected = listOf(HealthPermissionStatus(HealthPermissionGroup.STEPS, true))
        repository.permissionStatuses = expected
        val useCase = GetHealthPermissionStatusUseCaseImpl(repository)

        assertEquals(expected, useCase().first())
    }

    @Test
    fun `SyncHealthDataUseCase returns error if health service unavailable`() = runTest {
        val repository = FakeHealthRepository()
        repository.status = HealthServiceStatus.NOT_INSTALLED
        val useCase = SyncHealthDataUseCaseImpl(repository)

        val result = useCase()
        assertTrue(result.isFailure)
    }

    @Test
    fun `GetStepsUseCase returns steps from repository`() = runTest {
        val repository = FakeHealthRepository()
        repository.steps = 5000
        val useCase = GetStepsUseCaseImpl(repository)

        assertEquals(5000, useCase(LocalDate(2026, 8, 26)).first())
    }

    @Test
    fun `OpenHealthSettingsUseCase calls health manager`() {
        val manager = FakeHealthManager()
        val useCase = OpenHealthSettingsUseCaseImpl(manager)

        useCase()
        assertTrue(manager.openSettingsCalled)
    }

    @Test
    fun `RequestHealthPermissionsUseCase calls repository`() = runTest {
        val repository = FakeHealthRepository()
        val expected = setOf(HealthPermissionType.STEPS_READ)
        repository.requestedPermissionsResult = Result.success(expected)
        val useCase = RequestHealthPermissionsUseCaseImpl(repository)

        val result = useCase(HealthPermissionGroup.STEPS)
        assertEquals(expected, result.getOrNull())
    }
}
