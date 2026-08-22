package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel

/**
 * Mapper for converting [MealSlotDatabaseModel] to [MealSlotDomainModel] and vice versa.
 *
 * @property mealTypeMapper Mapper for the nested [MealTypeDomainModel].
 */
internal class MealSlotMapper(private val mealTypeMapper: MealTypeMapper) :
    BaseMapper<MealSlotDatabaseModel, MealSlotDomainModel> {
    override fun mapTo(model: MealSlotDatabaseModel): MealSlotDomainModel = MealSlotDomainModel(
        id = model.id,
        startTime = model.startTime,
        proteinsPercentage = model.proteinsPercentage,
        fatsPercentage = model.fatsPercentage,
        carbsPercentage = model.carbsPercentage,
        mealType = mealTypeMapper.mapTo(model.mealType),
        isConsumed = model.isConsumed
    )

    override fun mapFrom(model: MealSlotDomainModel): MealSlotDatabaseModel = MealSlotDatabaseModel(
        id = model.id,
        startTime = model.startTime,
        proteinsPercentage = model.proteinsPercentage,
        fatsPercentage = model.fatsPercentage,
        carbsPercentage = model.carbsPercentage,
        mealType = mealTypeMapper.mapFrom(model.mealType),
        isConsumed = model.isConsumed
    )
}
