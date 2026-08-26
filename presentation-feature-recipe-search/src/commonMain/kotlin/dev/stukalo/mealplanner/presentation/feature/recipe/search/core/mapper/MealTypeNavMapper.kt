package dev.stukalo.mealplanner.presentation.feature.recipe.search.core.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.model.MealTypeNavModel

/**
 * Mapper between [MealTypeDomainModel] and [MealTypeNavModel].
 */
internal object MealTypeNavMapper : BaseMapper<MealTypeDomainModel, MealTypeNavModel> {
    override fun mapTo(model: MealTypeDomainModel): MealTypeNavModel = when (model) {
        MealTypeDomainModel.BREAKFAST -> MealTypeNavModel.BREAKFAST
        MealTypeDomainModel.LUNCH -> MealTypeNavModel.LUNCH
        MealTypeDomainModel.DINNER -> MealTypeNavModel.DINNER
        MealTypeDomainModel.SNACK -> MealTypeNavModel.SNACK
        MealTypeDomainModel.TEATIME -> MealTypeNavModel.TEATIME
    }

    override fun mapFrom(model: MealTypeNavModel): MealTypeDomainModel = when (model) {
        MealTypeNavModel.BREAKFAST -> MealTypeDomainModel.BREAKFAST
        MealTypeNavModel.LUNCH -> MealTypeDomainModel.LUNCH
        MealTypeNavModel.DINNER -> MealTypeDomainModel.DINNER
        MealTypeNavModel.SNACK -> MealTypeDomainModel.SNACK
        MealTypeNavModel.TEATIME -> MealTypeDomainModel.TEATIME
    }
}
