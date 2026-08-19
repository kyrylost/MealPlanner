package dev.stukalo.mealplanner.data.repository.impl.health.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType

/**
 * Mapper for [HealthPermissionDataModel] to [HealthPermissionType].
 */
internal class HealthPermissionTypeMapper : BaseMapper<HealthPermissionDataModel, HealthPermissionType> {
    override fun mapTo(model: HealthPermissionDataModel): HealthPermissionType = when (model) {
        HealthPermissionDataModel.STEPS_READ -> HealthPermissionType.STEPS_READ
        HealthPermissionDataModel.WEIGHT_READ -> HealthPermissionType.WEIGHT_READ
        HealthPermissionDataModel.WEIGHT_WRITE -> HealthPermissionType.WEIGHT_WRITE
        HealthPermissionDataModel.NUTRITION_READ -> HealthPermissionType.NUTRITION_READ
        HealthPermissionDataModel.NUTRITION_WRITE -> HealthPermissionType.NUTRITION_WRITE
    }
}
