package dev.stukalo.mealplanner.data.database.source.statistics

import dev.stukalo.mealplanner.data.database.dao.statistics.WeightHistoryDao
import dev.stukalo.mealplanner.data.database.model.statistics.WeightHistoryDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class WeightHistoryDatabaseSource(private val weightHistoryDao: WeightHistoryDao) {
    suspend fun insert(item: WeightHistoryDatabaseModel): Result<Unit> = runCatching {
        weightHistoryDao.insert(item)
    }

    fun getAllAsFlow(): Flow<List<WeightHistoryDatabaseModel>> = weightHistoryDao.getAllAsFlow()

    fun getByPeriodAsFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<WeightHistoryDatabaseModel>> =
        weightHistoryDao.getByPeriodAsFlow(startDate, endDate)
}
