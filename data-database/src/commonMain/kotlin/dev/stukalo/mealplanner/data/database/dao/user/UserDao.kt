package dev.stukalo.mealplanner.data.database.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel.Companion.DEFAULT_USER_ID
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: UserDatabaseModel)

    @Query("SELECT * FROM UserDatabaseModel WHERE id = $DEFAULT_USER_ID LIMIT 1")
    suspend fun getUser(): UserDatabaseModel?

    @Query("SELECT count(*) FROM UserDatabaseModel")
    suspend fun count(): Int

    @Query("SELECT * FROM UserDatabaseModel WHERE id = $DEFAULT_USER_ID LIMIT 1")
    fun getUserAsFlow(): Flow<UserDatabaseModel?>
}
