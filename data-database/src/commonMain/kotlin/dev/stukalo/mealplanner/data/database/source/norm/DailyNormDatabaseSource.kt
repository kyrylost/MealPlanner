package dev.stukalo.mealplanner.data.database.source.norm

import dev.stukalo.mealplanner.data.database.dao.norm.DailyNormDao
import dev.stukalo.mealplanner.data.database.model.norm.DailyNormDatabaseModel

class DailyNormDatabaseSource(
    private val dao: DailyNormDao
) {
    suspend fun insert(dailyNorm: DailyNormDatabaseModel): Result<Unit> = runCatching {
        dao.insert(dailyNorm)
    }

    suspend fun getDailyNorm(): DailyNormDatabaseModel? = 
        dao.getDailyNorm()
}
