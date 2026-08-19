package dev.stukalo.mealplanner.data.repository.impl.health

import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.exception.HealthDataException
import dev.stukalo.mealplanner.data.preferences.settings.SettingsPreferencesDataSource
import dev.stukalo.mealplanner.data.repository.impl.health.mapper.HealthPermissionStatusMapper
import dev.stukalo.mealplanner.data.repository.impl.health.mapper.HealthPermissionTypeMapper
import dev.stukalo.mealplanner.data.repository.impl.health.mapper.HealthServiceStatusMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.DailyProgressMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.WeightHistoryMapper
import dev.stukalo.mealplanner.domain.model.exception.HealthException
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.repository.NutritionRepository
import dev.stukalo.mealplanner.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Implementation of [HealthRepository] that coordinates data between [HealthDataSource]
 * and internal repositories ([WeightRepository], [NutritionRepository]).
 *
 * @property healthDataSource Platform-specific health data source.
 * @property weightRepository Repository for weight data.
 * @property nutritionRepository Repository for nutrition data.
 * @property weightHistoryMapper Mapper for weight history data.
 * @property dailyProgressMapper Mapper for daily nutrition progress data.
 * @property healthServiceStatusMapper Mapper for health service status.
 * @property healthPermissionTypeMapper Mapper for health permission types.
 * @property healthPermissionStatusMapper Mapper for health permission statuses.
 * @property settingsPreferencesDataSource Data source for health-related preferences.
 * @property clock Provider for current time.
 */
internal class HealthRepositoryImpl(
    private val healthDataSource: HealthDataSource,
    private val weightRepository: WeightRepository,
    private val nutritionRepository: NutritionRepository,
    private val weightHistoryMapper: WeightHistoryMapper,
    private val dailyProgressMapper: DailyProgressMapper,
    private val healthServiceStatusMapper: HealthServiceStatusMapper,
    private val healthPermissionTypeMapper: HealthPermissionTypeMapper,
    private val healthPermissionStatusMapper: HealthPermissionStatusMapper,
    private val settingsPreferencesDataSource: SettingsPreferencesDataSource,
    private val clock: Clock
) : HealthRepository {
    companion object {
        private const val DEFAULT_SYNC_DAYS = 365
    }

    override suspend fun isAvailable(): Boolean = healthDataSource.isAvailable()

    override suspend fun getStatus(): HealthServiceStatus =
        healthServiceStatusMapper.mapTo(healthDataSource.getStatus())

    override suspend fun hasPermissions(): Boolean = healthDataSource.hasPermissions()

    override suspend fun getPermissionStatuses(): List<HealthPermissionStatus> =
        healthPermissionStatusMapper.mapListTo(healthDataSource.getPermissionStatuses())

    override suspend fun requestPermissions(group: HealthPermissionGroup?): Result<Set<HealthPermissionType>> =
        healthDataSource.requestPermissions(group?.let { healthPermissionStatusMapper.mapGroupToId(it) }).map { types ->
            healthPermissionTypeMapper.mapListTo(types.toList()).toSet()
        }

    override fun getStepsAsFlow(date: LocalDate): Flow<Int> = healthDataSource.getStepsAsFlow(date)

    override suspend fun syncAllData(): Result<Unit> = runCatching {
        val lastSyncTimestamp = settingsPreferencesDataSource.getLastHealthSyncTime().firstOrNull()
        val syncStartTime = if (lastSyncTimestamp != null) {
            Instant.fromEpochMilliseconds(lastSyncTimestamp)
        } else {
            val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val syncStartInstant = today.minus(DEFAULT_SYNC_DAYS, DateTimeUnit.DAY)
                .atStartOfDayIn(TimeZone.currentSystemDefault())
            Instant.fromEpochMilliseconds(syncStartInstant.toEpochMilliseconds())
        }

        // Sync weight
        healthDataSource.fetchWeightHistory(syncStartTime).getOrThrow().forEach { weightModel ->
            weightRepository.saveWeight(weightHistoryMapper.mapFromHealth(weightModel))
        }

        // Sync nutrients
        healthDataSource.fetchNutrientChanges(syncStartTime).getOrThrow().forEach { entry ->
            nutritionRepository.saveDailyProgress(dailyProgressMapper.mapFromHealth(entry.value))
        }

        settingsPreferencesDataSource.setLastHealthSyncTime(clock.now().toEpochMilliseconds())
    }.mapHealthError()

    override suspend fun writeWeight(weight: WeightHistoryDomainModel): Result<Unit> =
        healthDataSource.writeWeight(weightHistoryMapper.mapToHealth(weight)).mapHealthError()

    override suspend fun writeNutrition(date: LocalDate, progress: DailyProgressDomainModel): Result<Unit> =
        healthDataSource.writeNutrition(date, dailyProgressMapper.mapToHealth(progress)).mapHealthError()

    private fun <T> Result<T>.mapHealthError(): Result<T> = if (isFailure) {
        val healthException = when (val e = exceptionOrNull()) {
            is HealthDataException.ServiceUnavailable -> HealthException.Unavailable()
            is HealthDataException.ServiceNotInstalled -> HealthException.NotInstalled()
            is HealthDataException.InsufficientPermissions -> HealthException.PermissionsDenied()
            is HealthDataException.SyncError -> HealthException.SyncFailed(e.cause)
            is HealthDataException.WriteError -> HealthException.SyncFailed(e.cause)
            is HealthException -> e
            else -> HealthException.SyncFailed(e)
        }
        Result.failure(healthException)
    } else {
        this
    }
}
