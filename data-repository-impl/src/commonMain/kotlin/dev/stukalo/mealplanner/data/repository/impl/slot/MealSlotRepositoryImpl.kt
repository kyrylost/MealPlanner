package dev.stukalo.mealplanner.data.repository.impl.slot

import dev.stukalo.mealplanner.data.database.source.slot.MealSlotDatabaseSource
import dev.stukalo.mealplanner.data.repository.impl.slot.mapper.MealSlotMapper
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import dev.stukalo.mealplanner.domain.repository.MealSlotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Implementation of [dev.stukalo.mealplanner.domain.repository.MealSlotRepository] that handles meal slot data using a database source.
 * It provides default slots if the database is empty.
 *
 * @property mealSlotDatabaseSource The source for database operations.
 * @property mealSlotMapper Mapper to convert between domain and database models.
 * @property clock Clock provider for daily status calculation.
 */
internal class MealSlotRepositoryImpl(
    private val mealSlotDatabaseSource: MealSlotDatabaseSource,
    private val mealSlotMapper: MealSlotMapper,
    private val clock: Clock
) : MealSlotRepository {
    override fun getMealSlotsAsFlow(): Flow<List<MealSlotDomainModel>> = mealSlotDatabaseSource.getAllSlotsAsFlow()
        .onEach { slots ->
            if (slots.isEmpty()) {
                val defaults = getDefaultSlots()
                mealSlotDatabaseSource.insertAll(defaults.map { mealSlotMapper.mapFrom(it) })
            }
        }
        .filter { it.isNotEmpty() }
        .map { slots ->
            mealSlotMapper.mapListTo(slots)
        }

    override suspend fun updateConsumedStatus(id: Int, isConsumed: Boolean): Result<Unit> {
        val lastConsumedDate = if (isConsumed) {
            clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        } else {
            null
        }
        return mealSlotDatabaseSource.updateLastConsumedDate(id, lastConsumedDate)
    }

    override suspend fun updateSlotTime(id: Int, startTime: LocalTime): Result<Unit> =
        mealSlotDatabaseSource.updateSlotTime(id, startTime)

    override suspend fun resetDailyConsumedStatus(): Result<Unit> = mealSlotDatabaseSource.resetAllConsumedStatus()

    private fun getDefaultSlots(): List<MealSlotDomainModel> = listOf(
        MealSlotDomainModel(
            id = 0,
            startTime = LocalTime(BREAKFAST_HOUR, 0),
            proteinsPercentage = BREAKFAST_PROTEINS,
            fatsPercentage = BREAKFAST_FATS,
            carbsPercentage = BREAKFAST_CARBS,
            mealType = MealTypeDomainModel.BREAKFAST,
            isConsumed = false
        ),
        MealSlotDomainModel(
            id = 0,
            startTime = LocalTime(LUNCH_HOUR, 0),
            proteinsPercentage = LUNCH_PROTEINS,
            fatsPercentage = LUNCH_FATS,
            carbsPercentage = LUNCH_CARBS,
            mealType = MealTypeDomainModel.LUNCH,
            isConsumed = false
        ),
        MealSlotDomainModel(
            id = 0,
            startTime = LocalTime(DINNER_HOUR, 0),
            proteinsPercentage = DINNER_PROTEINS,
            fatsPercentage = DINNER_FATS,
            carbsPercentage = DINNER_CARBS,
            mealType = MealTypeDomainModel.DINNER,
            isConsumed = false
        )
    )

    companion object {
        private const val BREAKFAST_HOUR = 7
        private const val LUNCH_HOUR = 12
        private const val DINNER_HOUR = 19

        private const val BREAKFAST_PROTEINS = 35
        private const val BREAKFAST_FATS = 35
        private const val BREAKFAST_CARBS = 35

        private const val LUNCH_PROTEINS = 40
        private const val LUNCH_FATS = 40
        private const val LUNCH_CARBS = 40

        private const val DINNER_PROTEINS = 25
        private const val DINNER_FATS = 25
        private const val DINNER_CARBS = 25
    }
}
