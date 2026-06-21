package dev.stukalo.mealplanner.data.database.source.progress

import dev.stukalo.mealplanner.data.database.dao.progress.DailyProgressDao
import dev.stukalo.mealplanner.data.database.model.progress.DailyProgressDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class DailyProgressDatabaseSource(
    private val dao: DailyProgressDao
) {
    suspend fun insert(progress: DailyProgressDatabaseModel): Result<Unit> = runCatching {
        dao.insert(progress)
    }

    suspend fun getProgressByDate(date: LocalDate): DailyProgressDatabaseModel? =
        dao.getProgressByDate(date)

    fun getProgressByDateAsFlow(date: LocalDate): Flow<DailyProgressDatabaseModel?> =
        dao.getProgressByDateAsFlow(date)

    fun getAllProgressAsFlow(): Flow<List<DailyProgressDatabaseModel>> =
        dao.getAllProgressAsFlow()

    fun getProgressByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<DailyProgressDatabaseModel>> =
        dao.getProgressByPeriodAsFlow(startDate, endDate)
}
