package dev.stukalo.mealplanner.data.repository.impl.statistics.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.progress.DailyProgressDatabaseModel
import dev.stukalo.mealplanner.data.health.model.NutritionHealthModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel

/**
 * Mapper for [DailyProgressDatabaseModel] and [DailyProgressDomainModel].
 */
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

    fun mapFromHealth(model: NutritionHealthModel): DailyProgressDomainModel = with(model) {
        DailyProgressDomainModel(
            date = date,
            consumedCalories = calories,
            consumedProteins = proteins,
            consumedFats = fats,
            consumedCarbohydrates = carbohydrates
        )
    }

    fun mapToHealth(model: DailyProgressDomainModel): NutritionHealthModel = with(model) {
        NutritionHealthModel(
            date = date,
            calories = consumedCalories,
            proteins = consumedProteins,
            fats = consumedFats,
            carbohydrates = consumedCarbohydrates
        )
    }
}
