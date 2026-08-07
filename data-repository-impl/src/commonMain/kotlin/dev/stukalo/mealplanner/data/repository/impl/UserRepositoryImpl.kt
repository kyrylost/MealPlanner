package dev.stukalo.mealplanner.data.repository.impl

import dev.stukalo.mealplanner.data.database.model.statistics.WeightHistoryDatabaseModel
import dev.stukalo.mealplanner.data.database.source.statistics.WeightHistoryDatabaseSource
import dev.stukalo.mealplanner.data.database.source.user.UserDatabaseSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.UserMapper
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Implementation of [UserRepository] optimized for a single-user profile.
 *
 * @property userDatabaseSource Data source for user profile.
 * @property weightHistoryDatabaseSource Data source for weight history.
 * @property userMapper Mapper for user models.
 * @property clock Clock provider for current date.
 */
internal class UserRepositoryImpl(
    private val userDatabaseSource: UserDatabaseSource,
    private val weightHistoryDatabaseSource: WeightHistoryDatabaseSource,
    private val userMapper: UserMapper,
    private val clock: Clock
) : UserRepository {
    override suspend fun insert(user: UserDomainModel): Result<Unit> {
        val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val userInsertResult = userDatabaseSource.insert(userMapper.mapFrom(user))

        return userInsertResult.mapCatching {
            weightHistoryDatabaseSource.insert(
                WeightHistoryDatabaseModel(
                    date = today,
                    weight = user.weight
                )
            ).getOrThrow()
        }
    }

    override suspend fun count(): Int = userDatabaseSource.count()

    override suspend fun getUser(): UserDomainModel? {
        val userModel = userDatabaseSource.getUser() ?: return null
        val weight = weightHistoryDatabaseSource.getLatestWeight()?.weight ?: 0.0
        return userMapper.mapTo(userModel, weight)
    }

    override fun getUserAsFlow(): Flow<UserDomainModel?> = combine(
        userDatabaseSource.getUserAsFlow(),
        weightHistoryDatabaseSource.getLatestWeightAsFlow()
    ) { user, latestWeight ->
        user?.let {
            userMapper.mapTo(it, latestWeight?.weight ?: 0.0)
        }
    }
}
