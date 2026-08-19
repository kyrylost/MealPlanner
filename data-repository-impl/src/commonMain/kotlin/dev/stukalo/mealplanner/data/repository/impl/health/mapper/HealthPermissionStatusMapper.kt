package dev.stukalo.mealplanner.data.repository.impl.health.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.model.HealthPermissionStatusDataModel
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus

/**
 * Mapper for [HealthPermissionStatusDataModel] and [HealthPermissionStatus].
 */
internal class HealthPermissionStatusMapper : BaseMapper<HealthPermissionStatusDataModel, HealthPermissionStatus> {
    override fun mapTo(model: HealthPermissionStatusDataModel): HealthPermissionStatus = HealthPermissionStatus(
        group = when (model.id) {
            HealthDataSource.ID_STEPS -> HealthPermissionGroup.STEPS
            HealthDataSource.ID_WEIGHT_READ -> HealthPermissionGroup.WEIGHT_READ
            HealthDataSource.ID_WEIGHT_WRITE -> HealthPermissionGroup.WEIGHT_WRITE
            HealthDataSource.ID_NUTRITION_READ -> HealthPermissionGroup.NUTRITION_READ
            HealthDataSource.ID_NUTRITION_WRITE -> HealthPermissionGroup.NUTRITION_WRITE
            HealthDataSource.ID_INTEGRATED -> HealthPermissionGroup.INTEGRATED
            else -> throw IllegalArgumentException("Unknown health permission ID: ${model.id}")
        },
        isGranted = model.isGranted
    )

    /**
     * Maps a domain permission group to a platform-specific ID.
     *
     * @param group The permission group.
     * @return The platform-specific ID string.
     */
    fun mapGroupToId(group: HealthPermissionGroup): String = when (group) {
        HealthPermissionGroup.STEPS -> HealthDataSource.ID_STEPS
        HealthPermissionGroup.WEIGHT_READ -> HealthDataSource.ID_WEIGHT_READ
        HealthPermissionGroup.WEIGHT_WRITE -> HealthDataSource.ID_WEIGHT_WRITE
        HealthPermissionGroup.NUTRITION_READ -> HealthDataSource.ID_NUTRITION_READ
        HealthPermissionGroup.NUTRITION_WRITE -> HealthDataSource.ID_NUTRITION_WRITE
        HealthPermissionGroup.INTEGRATED -> HealthDataSource.ID_INTEGRATED
    }
}
