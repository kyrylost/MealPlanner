package dev.stukalo.mealplanner.presentation.feature.settings.core.mapper

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.error_health_permissions_denied
import dev.stukalo.mealplanner.core.localization.error_health_sync_failed
import dev.stukalo.mealplanner.core.localization.error_health_unavailable
import dev.stukalo.mealplanner.core.localization.error_unknown
import dev.stukalo.mealplanner.domain.model.exception.HealthException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [HealthException] to a localized [StringResource].
 */
fun HealthException.toMessage(): StringResource = when (this) {
    is HealthException.Unavailable -> Res.string.error_health_unavailable
    is HealthException.PermissionsDenied -> Res.string.error_health_permissions_denied
    is HealthException.SyncFailed -> Res.string.error_health_sync_failed
    else -> Res.string.error_unknown
}
