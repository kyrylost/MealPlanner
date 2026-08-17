package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.norm.DailyNormDatabaseModel
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel

internal class DailyNormMapper : BaseMapper<DailyNormDatabaseModel, DailyNormDomainModel> {
    override fun mapTo(model: DailyNormDatabaseModel): DailyNormDomainModel = with(model) {
        DailyNormDomainModel(
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbohydrates = carbohydrates
        )
    }

    override fun mapFrom(model: DailyNormDomainModel): DailyNormDatabaseModel = with(model) {
        DailyNormDatabaseModel(
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbohydrates = carbohydrates
        )
    }
}
