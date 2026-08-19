package dev.stukalo.mealplanner.data.repository.impl.health.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.health.model.HealthServiceStatusDataModel
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus

/**
 * Mapper for [HealthServiceStatusDataModel] to [HealthServiceStatus].
 */
internal class HealthServiceStatusMapper : BaseMapper<HealthServiceStatusDataModel, HealthServiceStatus> {
    override fun mapTo(model: HealthServiceStatusDataModel): HealthServiceStatus = when (model) {
        HealthServiceStatusDataModel.AVAILABLE -> HealthServiceStatus.AVAILABLE
        HealthServiceStatusDataModel.NOT_SUPPORTED -> HealthServiceStatus.NOT_SUPPORTED
        HealthServiceStatusDataModel.NOT_INSTALLED -> HealthServiceStatus.NOT_INSTALLED
    }
}
