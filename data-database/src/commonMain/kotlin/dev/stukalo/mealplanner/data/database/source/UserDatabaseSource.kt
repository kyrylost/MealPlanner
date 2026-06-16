package dev.stukalo.mealplanner.data.database.source

import dev.stukalo.mealplanner.data.database.dao.UserDao
import dev.stukalo.mealplanner.data.database.model.UserDatabaseModel
import kotlinx.coroutines.flow.Flow

class UserDatabaseSource(
    private val dao: UserDao
) {
    suspend fun insert(user: UserDatabaseModel): Result<Unit> = runCatching {
        dao.insert(user)
    }

    suspend fun count() = dao.count()

    suspend fun getUser(id: Long): UserDatabaseModel? = dao.getUser(id)

    fun getAllAsFlow(): Flow<List<UserDatabaseModel>> = dao.getAllAsFlow()
}
