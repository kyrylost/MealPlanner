package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel

internal class MealSlotMapper(
    private val mealTypeMapper: MealTypeMapper,
) : BaseMapper<MealSlotDatabaseModel, MealSlotDomainModel> {
    override fun mapTo(model: MealSlotDatabaseModel): MealSlotDomainModel {
        return MealSlotDomainModel(
            id = model.id,
            name = model.name,
            startTime = model.startTime,
            proteinsPercentage = model.proteinsPercentage,
            fatsPercentage = model.fatsPercentage,
            carbsPercentage = model.carbsPercentage,
            mealTypes = model.mealTypes.map { mealTypeMapper.mapTo(it) },
            isConsumed = model.isConsumed
        )
    }

    override fun mapFrom(model: MealSlotDomainModel): MealSlotDatabaseModel {
        return MealSlotDatabaseModel(
            id = model.id,
            name = model.name,
            startTime = model.startTime,
            proteinsPercentage = model.proteinsPercentage,
            fatsPercentage = model.fatsPercentage,
            carbsPercentage = model.carbsPercentage,
            mealTypes = model.mealTypes.map { mealTypeMapper.mapFrom(it) },
            isConsumed = model.isConsumed
        )
    }
}