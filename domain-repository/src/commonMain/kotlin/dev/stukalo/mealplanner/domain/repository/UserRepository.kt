package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insert(user: UserDomainModel): Result<Unit>
    suspend fun count(): Int
    suspend fun getUser(id: Long): UserDomainModel?
    fun getAllAsFlow(): Flow<List<UserDomainModel>>
}
