package dev.stukalo.mealplanner.presentation.feature.recipe.search.core.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.model.FilterNavModel

/**
 * Mapper between [FilterDomainModel] and [FilterNavModel].
 */
internal object FilterNavMapper : BaseMapper<FilterDomainModel, FilterNavModel> {
    override fun mapTo(model: FilterDomainModel): FilterNavModel = FilterNavModel(
        minCalories = model.minCalories,
        maxCalories = model.maxCalories,
        minProteins = model.minProteins,
        maxProteins = model.maxProteins,
        minFats = model.minFats,
        maxFats = model.maxFats,
        minCarbs = model.minCarbs,
        maxCarbs = model.maxCarbs,
        mealTypes = model.mealTypes.map(MealTypeNavMapper::mapTo)
    )

    override fun mapFrom(model: FilterNavModel): FilterDomainModel = FilterDomainModel(
        minCalories = model.minCalories,
        maxCalories = model.maxCalories,
        minProteins = model.minProteins,
        maxProteins = model.maxProteins,
        minFats = model.minFats,
        maxFats = model.maxFats,
        minCarbs = model.minCarbs,
        maxCarbs = model.maxCarbs,
        mealTypes = model.mealTypes.map(MealTypeNavMapper::mapFrom)
    )
}
