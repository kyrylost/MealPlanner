package dev.stukalo.mealplanner.data.repository.impl.statistics.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.database.model.statistics.WeightHistoryDatabaseModel
import dev.stukalo.mealplanner.data.health.model.WeightHealthModel
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel

/**
 * Mapper for [WeightHistoryDatabaseModel] and [WeightHistoryDomainModel].
 */
internal class WeightHistoryMapper : BaseMapper<WeightHistoryDatabaseModel, WeightHistoryDomainModel> {
    override fun mapTo(model: WeightHistoryDatabaseModel): WeightHistoryDomainModel = WeightHistoryDomainModel(
        date = model.date,
        weight = model.weight
    )

    override fun mapFrom(model: WeightHistoryDomainModel): WeightHistoryDatabaseModel = WeightHistoryDatabaseModel(
        date = model.date,
        weight = model.weight
    )

    override fun mapListTo(model: List<WeightHistoryDatabaseModel>): List<WeightHistoryDomainModel> = model.map {
        mapTo(it)
    }

    fun mapFromHealth(model: WeightHealthModel): WeightHistoryDomainModel = WeightHistoryDomainModel(
        date = model.date,
        weight = model.weight
    )

    fun mapToHealth(model: WeightHistoryDomainModel): WeightHealthModel = WeightHealthModel(
        date = model.date,
        weight = model.weight
    )
}
