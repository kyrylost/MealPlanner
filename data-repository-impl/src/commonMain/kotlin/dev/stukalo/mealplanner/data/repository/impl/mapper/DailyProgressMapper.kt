package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.common.core.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.progress.DailyProgressDatabaseModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel

internal class DailyProgressMapper : BaseMapper<DailyProgressDatabaseModel, DailyProgressDomainModel> {
    override fun mapTo(model: DailyProgressDatabaseModel): DailyProgressDomainModel = with(model) {
        DailyProgressDomainModel(
            date = date,
            consumedCalories = consumedCalories,
            consumedProteins = consumedProteins,
            consumedFats = consumedFats,
            consumedCarbohydrates = consumedCarbohydrates
        )
    }

    override fun mapFrom(model: DailyProgressDomainModel): DailyProgressDatabaseModel = with(model) {
        DailyProgressDatabaseModel(
            date = date,
            consumedCalories = consumedCalories,
            consumedProteins = consumedProteins,
            consumedFats = consumedFats,
            consumedCarbohydrates = consumedCarbohydrates
        )
    }
}
