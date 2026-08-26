package dev.stukalo.mealplanner.presentation.core.ui.mapper

import dev.stukalo.mealplanner.core.common.exception.ApiException
import dev.stukalo.mealplanner.core.common.exception.AppException
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.error_api_connection
import dev.stukalo.mealplanner.core.localization.error_api_not_found
import dev.stukalo.mealplanner.core.localization.error_api_server
import dev.stukalo.mealplanner.core.localization.error_api_timeout
import dev.stukalo.mealplanner.core.localization.error_api_unauthorized
import dev.stukalo.mealplanner.core.localization.error_unknown
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [AppException] to a localized [StringResource].
 *
 * This function handles common system and API exceptions.
 * Feature-specific exceptions should be handled in their respective modules.
 */
fun AppException.toMessage(): StringResource = when (this) {
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
