package dev.stukalo.mealplanner.presentation.core.ui.mapper

import dev.stukalo.mealplanner.core.common.exception.ApiException
import dev.stukalo.mealplanner.core.common.exception.AppException
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.barcode_scanner_not_found
import dev.stukalo.mealplanner.core.localization.error_api_connection
import dev.stukalo.mealplanner.core.localization.error_api_not_found
import dev.stukalo.mealplanner.core.localization.error_api_server
import dev.stukalo.mealplanner.core.localization.error_api_timeout
import dev.stukalo.mealplanner.core.localization.error_api_unauthorized
import dev.stukalo.mealplanner.core.localization.error_health_permissions_denied
import dev.stukalo.mealplanner.core.localization.error_health_sync_failed
import dev.stukalo.mealplanner.core.localization.error_health_unavailable
import dev.stukalo.mealplanner.core.localization.error_unknown
import dev.stukalo.mealplanner.core.localization.recipe_details_not_found
import dev.stukalo.mealplanner.core.localization.statistics_meal_order_error
import dev.stukalo.mealplanner.core.localization.validation_error_date_empty
import dev.stukalo.mealplanner.core.localization.validation_error_date_future
import dev.stukalo.mealplanner.core.localization.validation_error_date_invalid
import dev.stukalo.mealplanner.core.localization.validation_error_gender_empty
import dev.stukalo.mealplanner.core.localization.validation_error_height_empty
import dev.stukalo.mealplanner.core.localization.validation_error_height_invalid
import dev.stukalo.mealplanner.core.localization.validation_error_name_empty
import dev.stukalo.mealplanner.core.localization.validation_error_name_too_short
import dev.stukalo.mealplanner.core.localization.validation_error_weight_empty
import dev.stukalo.mealplanner.core.localization.validation_error_weight_invalid
import dev.stukalo.mealplanner.domain.model.exception.HealthException
import dev.stukalo.mealplanner.domain.model.exception.MealSlotException
import dev.stukalo.mealplanner.domain.model.exception.ProductException
import dev.stukalo.mealplanner.domain.model.exception.RecipeException
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [AppException] to a localized [StringResource].
 */
fun AppException.toMessage(): StringResource = when (this) {
    // Validation Errors
    is ValidationException.Name.Empty -> Res.string.validation_error_name_empty
    is ValidationException.Name.TooShort -> Res.string.validation_error_name_too_short
    is ValidationException.Date.Empty -> Res.string.validation_error_date_empty
    is ValidationException.Date.Invalid -> Res.string.validation_error_date_invalid
    is ValidationException.Date.Future -> Res.string.validation_error_date_future
    is ValidationException.Height.Empty -> Res.string.validation_error_height_empty
    is ValidationException.Height.Invalid -> Res.string.validation_error_height_invalid
    is ValidationException.Weight.Empty -> Res.string.validation_error_weight_empty
    is ValidationException.Weight.Invalid -> Res.string.validation_error_weight_invalid
    is ValidationException.Gender.NotSelected -> Res.string.validation_error_gender_empty

    // Health Sync Errors
    is HealthException.Unavailable -> Res.string.error_health_unavailable
    is HealthException.PermissionsDenied -> Res.string.error_health_permissions_denied
    is HealthException.SyncFailed -> Res.string.error_health_sync_failed

    // Domain Specific Errors
    is MealSlotException.MealOrderViolation -> Res.string.statistics_meal_order_error
    is ProductException.ProductNotFound -> Res.string.barcode_scanner_not_found
    is RecipeException.RecipeNotFound -> Res.string.recipe_details_not_found

    // API Errors
    is ApiException.ClientError.NotAuthorizedException -> Res.string.error_api_unauthorized
    is ApiException.ClientError.NotFoundException -> Res.string.error_api_not_found
    is ApiException.ServerError -> Res.string.error_api_server
    is ApiException.ConnectionApiException -> Res.string.error_api_connection
    is ApiException.TimeoutApiException -> Res.string.error_api_timeout

    else -> Res.string.error_unknown
}

/**
 * Maps [Throwable] to a localized [StringResource].
 */
fun Throwable.toMessage(): StringResource = (this as? AppException)?.toMessage() ?: Res.string.error_unknown
