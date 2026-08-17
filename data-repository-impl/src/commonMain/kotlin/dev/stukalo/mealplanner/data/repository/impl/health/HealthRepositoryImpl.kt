package dev.stukalo.mealplanner.data.repository.impl.health

import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.exception.HealthDataException
import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.data.health.model.HealthServiceStatusDataModel
import dev.stukalo.mealplanner.data.preferences.settings.SettingsPreferencesDataSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.DailyProgressMapper
import dev.stukalo.mealplanner.data.repository.impl.mapper.WeightHistoryMapper
import dev.stukalo.mealplanner.domain.model.exception.HealthException
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
 * @property settingsPreferencesDataSource Data source for health-related preferences.
 * @property clock Provider for current time.
 */
internal class HealthRepositoryImpl(
    private val healthDataSource: HealthDataSource,
    private val weightRepository: WeightRepository,
    private val nutritionRepository: NutritionRepository,
    private val weightHistoryMapper: WeightHistoryMapper,
    private val dailyProgressMapper: DailyProgressMapper,
    private val settingsPreferencesDataSource: SettingsPreferencesDataSource,
    private val clock: Clock
) : HealthRepository {
    companion object {
        private const val DEFAULT_SYNC_DAYS = 365
    }

    override suspend fun isAvailable(): Boolean = healthDataSource.isAvailable()

    override suspend fun getStatus(): HealthServiceStatus = healthDataSource.getStatus().toDomain()

    override suspend fun hasPermissions(): Boolean = healthDataSource.hasPermissions()

    override suspend fun getGrantedPermissions(): Set<String> = healthDataSource.getGrantedPermissions()

    override fun getPermissionString(type: HealthPermissionType): String =
        healthDataSource.getPermissionString(type.toData())

    override suspend fun requestPermissions(): Result<Boolean> = healthDataSource.requestPermissions()

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

    private fun HealthServiceStatusDataModel.toDomain(): HealthServiceStatus = when (this) {
        HealthServiceStatusDataModel.AVAILABLE -> HealthServiceStatus.AVAILABLE
        HealthServiceStatusDataModel.NOT_SUPPORTED -> HealthServiceStatus.NOT_SUPPORTED
        HealthServiceStatusDataModel.NOT_INSTALLED -> HealthServiceStatus.NOT_INSTALLED
    }

    private fun HealthPermissionType.toData(): HealthPermissionDataModel = when (this) {
        HealthPermissionType.STEPS_READ -> HealthPermissionDataModel.STEPS_READ
        HealthPermissionType.WEIGHT_READ -> HealthPermissionDataModel.WEIGHT_READ
        HealthPermissionType.WEIGHT_WRITE -> HealthPermissionDataModel.WEIGHT_WRITE
        HealthPermissionType.NUTRITION_READ -> HealthPermissionDataModel.NUTRITION_READ
        HealthPermissionType.NUTRITION_WRITE -> HealthPermissionDataModel.NUTRITION_WRITE
    }

    override fun getPermissionStrings(): Set<String> = healthDataSource.getPermissionStrings()
}
