package dev.stukalo.mealplanner.data.repository.impl

import dev.stukalo.mealplanner.data.database.source.slot.MealSlotDatabaseSource
import dev.stukalo.mealplanner.data.repository.impl.mapper.MealSlotMapper
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import dev.stukalo.mealplanner.domain.repository.MealScheduleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalTime

internal class MealScheduleRepositoryImpl(
    private val mealSlotDatabaseSource: MealSlotDatabaseSource,
    private val mealSlotMapper: MealSlotMapper
) : MealScheduleRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMealSlotsAsFlow(): Flow<List<MealSlotDomainModel>> =
        mealSlotDatabaseSource.getAllSlotsAsFlow().flatMapLatest { slots ->
            if (slots.isEmpty()) {
                flow {
                    val defaults = getDefaultSlots()
                    mealSlotDatabaseSource.insertAll(defaults.map { mealSlotMapper.mapFrom(it) })
                }
            } else {
                flow {
                    emit(slots.map { mealSlotMapper.mapTo(it) })
                }
            }
        }

    override suspend fun updateConsumedStatus(id: Int, isConsumed: Boolean): Result<Unit> =
        mealSlotDatabaseSource.updateConsumedStatus(id, isConsumed)

    override suspend fun resetDailyConsumedStatus(): Result<Unit> = mealSlotDatabaseSource.resetAllConsumedStatus()

    private fun getDefaultSlots(): List<MealSlotDomainModel> = listOf(
        MealSlotDomainModel(
            id = 0,
            name = "Breakfast",
            startTime = LocalTime(7, 0),
            proteinsPercentage = 35,
            fatsPercentage = 35,
            carbsPercentage = 35,
            mealTypes = listOf(MealTypeDomainModel.BREAKFAST),
            isConsumed = false
        ),
        MealSlotDomainModel(
            id = 0,
            name = "Lunch",
            startTime = LocalTime(12, 0),
            proteinsPercentage = 40,
            fatsPercentage = 40,
            carbsPercentage = 40,
            mealTypes = listOf(MealTypeDomainModel.LUNCH),
            isConsumed = false
        ),
        MealSlotDomainModel(
            id = 0,
            name = "Dinner",
            startTime = LocalTime(19, 0),
            proteinsPercentage = 25,
            fatsPercentage = 25,
            carbsPercentage = 25,
            mealTypes = listOf(MealTypeDomainModel.DINNER),
            isConsumed = false
        )
    )
}
