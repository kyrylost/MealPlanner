package dev.stukalo.mealplanner.data.repository.impl

import dev.stukalo.mealplanner.data.database.source.UserDatabaseSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.UserMapper
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val userDatabaseSource: UserDatabaseSource,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun insert(user: UserDomainModel) {
        userDatabaseSource.insert(userMapper.mapFrom(user))
    }

    override suspend fun count(): Int {
        return userDatabaseSource.count()
    }

    override suspend fun getUser(id: Long): UserDomainModel? {
        return userDatabaseSource.getUser(id)?.let { userMapper.mapTo(it) }
    }

    override fun getAllAsFlow(): Flow<List<UserDomainModel>> {
        return userDatabaseSource.getAllAsFlow().map { list ->
            userMapper.mapListTo(list)
        }
    }
}
