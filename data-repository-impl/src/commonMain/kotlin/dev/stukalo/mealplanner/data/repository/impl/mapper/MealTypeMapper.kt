package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.slot.MealTypeDatabaseModel
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel

internal class MealTypeMapper : BaseMapper<MealTypeDatabaseModel, MealTypeDomainModel> {
    override fun mapTo(model: MealTypeDatabaseModel): MealTypeDomainModel {
        return when (model) {
            MealTypeDatabaseModel.BREAKFAST -> MealTypeDomainModel.BREAKFAST
            MealTypeDatabaseModel.LUNCH -> MealTypeDomainModel.LUNCH
            MealTypeDatabaseModel.DINNER -> MealTypeDomainModel.DINNER
            MealTypeDatabaseModel.SNACK -> MealTypeDomainModel.SNACK
            MealTypeDatabaseModel.TEATIME -> MealTypeDomainModel.TEATIME
        }
    }

    override fun mapFrom(model: MealTypeDomainModel): MealTypeDatabaseModel {
        return when (model) {
            MealTypeDomainModel.BREAKFAST -> MealTypeDatabaseModel.BREAKFAST
            MealTypeDomainModel.LUNCH -> MealTypeDatabaseModel.LUNCH
            MealTypeDomainModel.DINNER -> MealTypeDatabaseModel.DINNER
            MealTypeDomainModel.SNACK -> MealTypeDatabaseModel.SNACK
            MealTypeDomainModel.TEATIME -> MealTypeDatabaseModel.TEATIME
        }
    }
}
