package dev.stukalo.mealplanner.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import dev.stukalo.mealplanner.data.database.model.UserDatabaseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(item: UserDatabaseModel)

    @Query("SELECT * FROM UserDatabaseModel WHERE id = :id")
    suspend fun getUser(id: Long): UserDatabaseModel?

    @Query("SELECT count(*) FROM UserDatabaseModel")
    suspend fun count(): Int

    @Query("SELECT * FROM UserDatabaseModel")
    fun getAllAsFlow(): Flow<List<UserDatabaseModel>>
}