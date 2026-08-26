package dev.stukalo.mealplanner.data.repository.impl.slot.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Mapper for converting [MealSlotDatabaseModel] to [MealSlotDomainModel] and vice versa.
 *
 * @property mealTypeMapper Mapper for the nested [MealTypeDomainModel].
 * @property clock Clock provider for calculating the current date.
 */
internal class MealSlotMapper(private val mealTypeMapper: MealTypeMapper, private val clock: Clock) :
    BaseMapper<MealSlotDatabaseModel, MealSlotDomainModel> {
    override fun mapTo(model: MealSlotDatabaseModel): MealSlotDomainModel {
        val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        return MealSlotDomainModel(
            id = model.id,
            startTime = model.startTime,
            proteinsPercentage = model.proteinsPercentage,
            fatsPercentage = model.fatsPercentage,
            carbsPercentage = model.carbsPercentage,
            mealType = mealTypeMapper.mapTo(model.mealType),
            isConsumed = model.lastConsumedDate == today
        )
    }

    override fun mapFrom(model: MealSlotDomainModel): MealSlotDatabaseModel = MealSlotDatabaseModel(
        id = model.id,
        startTime = model.startTime,
        proteinsPercentage = model.proteinsPercentage,
        fatsPercentage = model.fatsPercentage,
        carbsPercentage = model.carbsPercentage,
        mealType = mealTypeMapper.mapFrom(model.mealType),
        lastConsumedDate = null // Default for new slots
    )
}
