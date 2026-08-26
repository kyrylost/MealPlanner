package dev.stukalo.mealplanner.presentation.feature.settings.core.mapper

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.error_health_permissions_denied
import dev.stukalo.mealplanner.core.localization.error_health_sync_failed
import dev.stukalo.mealplanner.core.localization.error_health_unavailable
import dev.stukalo.mealplanner.core.localization.error_unknown
import dev.stukalo.mealplanner.core.localization.validation_error_height_empty
import dev.stukalo.mealplanner.core.localization.validation_error_height_invalid
import dev.stukalo.mealplanner.core.localization.validation_error_weight_empty
import dev.stukalo.mealplanner.core.localization.validation_error_weight_invalid
import dev.stukalo.mealplanner.domain.model.exception.HealthException
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps feature-specific exceptions to localized messages for the Settings screen.
 */
fun Throwable.toMessage(): StringResource = when (this) {
    is HealthException -> when (this) {
        is HealthException.Unavailable -> Res.string.error_health_unavailable
        is HealthException.PermissionsDenied -> Res.string.error_health_permissions_denied
        is HealthException.SyncFailed -> Res.string.error_health_sync_failed
        else -> Res.string.error_unknown
    }
    is ValidationException -> when (this) {
        is ValidationException.Height.Empty -> Res.string.validation_error_height_empty
        is ValidationException.Height.Invalid -> Res.string.validation_error_height_invalid
        is ValidationException.Weight.Empty -> Res.string.validation_error_weight_empty
        is ValidationException.Weight.Invalid -> Res.string.validation_error_weight_invalid
        else -> Res.string.error_unknown
    }
    else -> Res.string.error_unknown
}
