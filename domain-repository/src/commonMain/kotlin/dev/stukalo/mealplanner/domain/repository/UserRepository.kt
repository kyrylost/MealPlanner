package dev.stukalo.mealplanner.domain.repository

import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing user profile data.
 */
interface UserRepository {
    /**
     * Inserts or updates a user profile.
     *
     * @param user The user profile data to save.
     */
    suspend fun insert(user: UserDomainModel): Result<Unit>

    /**
     * Returns the number of users in the database.
     */
    suspend fun count(): Int

    /**
     * Returns the current user profile.
     */
    suspend fun getUser(): UserDomainModel?

    /**
     * Returns the current user profile as a flow.
     */
    fun getUserAsFlow(): Flow<UserDomainModel?>
}
