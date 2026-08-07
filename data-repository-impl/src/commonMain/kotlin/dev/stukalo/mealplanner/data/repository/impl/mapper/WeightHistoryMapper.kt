package dev.stukalo.mealplanner.data.repository.impl.mapper

import dev.stukalo.mealplanner.data.database.model.statistics.WeightHistoryDatabaseModel
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel

/**
 * Mapper for [WeightHistoryDatabaseModel] and [WeightHistoryDomainModel].
 */
internal class WeightHistoryMapper {
    fun mapTo(model: WeightHistoryDatabaseModel): WeightHistoryDomainModel = WeightHistoryDomainModel(
        date = model.date,
        weight = model.weight
    )

    fun mapFrom(model: WeightHistoryDomainModel): WeightHistoryDatabaseModel = WeightHistoryDatabaseModel(
        date = model.date,
        weight = model.weight
    )

    fun mapListTo(list: List<WeightHistoryDatabaseModel>): List<WeightHistoryDomainModel> = list.map { mapTo(it) }
}
