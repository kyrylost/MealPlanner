package dev.stukalo.mealplanner.data.database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel.Companion.DEFAULT_USER_ID
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the user profile table.
 */
@Dao
interface UserDao {
    /**
     * Inserts or replaces a user profile.
     *
     * @param item The user database model.
     */
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: UserDatabaseModel)

    /**
     * Retrieves the single user profile.
     */
    @Query("SELECT * FROM UserDatabaseModel WHERE id = $DEFAULT_USER_ID LIMIT 1")
    suspend fun getUser(): UserDatabaseModel?

    /**
     * Returns the number of users in the table.
     */
    @Query("SELECT count(*) FROM UserDatabaseModel")
    suspend fun count(): Int

    /**
     * Returns the single user profile as a flow.
     */
    @Query("SELECT * FROM UserDatabaseModel WHERE id = $DEFAULT_USER_ID LIMIT 1")
    fun getUserAsFlow(): Flow<UserDatabaseModel?>
}
