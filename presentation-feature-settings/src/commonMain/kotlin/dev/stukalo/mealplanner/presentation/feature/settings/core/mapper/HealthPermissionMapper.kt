package dev.stukalo.mealplanner.presentation.feature.settings.core.mapper

import dev.stukalo.mealplanner.core.common.mapper.BaseMapper
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.settings_health_sync_ios_desc
import dev.stukalo.mealplanner.core.localization.settings_health_sync_ios_title
import dev.stukalo.mealplanner.core.localization.settings_health_sync_nutrition_read
import dev.stukalo.mealplanner.core.localization.settings_health_sync_nutrition_read_desc
import dev.stukalo.mealplanner.core.localization.settings_health_sync_nutrition_write
import dev.stukalo.mealplanner.core.localization.settings_health_sync_nutrition_write_desc
import dev.stukalo.mealplanner.core.localization.settings_health_sync_steps_read
import dev.stukalo.mealplanner.core.localization.settings_health_sync_steps_read_desc
import dev.stukalo.mealplanner.core.localization.settings_health_sync_weight_read
import dev.stukalo.mealplanner.core.localization.settings_health_sync_weight_read_desc
import dev.stukalo.mealplanner.core.localization.settings_health_sync_weight_write
import dev.stukalo.mealplanner.core.localization.settings_health_sync_weight_write_desc
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import dev.stukalo.mealplanner.presentation.feature.settings.core.model.HealthPermissionOption

/**
 * Mapper for converting [HealthPermissionStatus] to [HealthPermissionOption].
 */
class HealthPermissionMapper : BaseMapper<HealthPermissionStatus, HealthPermissionOption> {

    private val resourceRegistry = mapOf(
        HealthPermissionGroup.STEPS to
            (Res.string.settings_health_sync_steps_read to Res.string.settings_health_sync_steps_read_desc),
        HealthPermissionGroup.WEIGHT_READ to
            (Res.string.settings_health_sync_weight_read to Res.string.settings_health_sync_weight_read_desc),
        HealthPermissionGroup.WEIGHT_WRITE to
            (Res.string.settings_health_sync_weight_write to Res.string.settings_health_sync_weight_write_desc),
        HealthPermissionGroup.NUTRITION_READ to
            (Res.string.settings_health_sync_nutrition_read to Res.string.settings_health_sync_nutrition_read_desc),
        HealthPermissionGroup.NUTRITION_WRITE to
            (Res.string.settings_health_sync_nutrition_write to Res.string.settings_health_sync_nutrition_write_desc),
        HealthPermissionGroup.INTEGRATED to
            (Res.string.settings_health_sync_ios_title to Res.string.settings_health_sync_ios_desc)
    )

    override fun mapTo(model: HealthPermissionStatus): HealthPermissionOption {
        val (title, description) = resourceRegistry[model.group]
            ?: throw IllegalArgumentException("Missing resources for permission group: ${model.group}")

        return HealthPermissionOption(
            group = model.group,
            title = title,
            description = description,
            isGranted = model.isGranted
        )
    }
}
