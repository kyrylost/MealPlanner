package dev.stukalo.mealplanner.presentation.core.ui.mapper

import dev.stukalo.mealplanner.common.core.exception.AppException
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.error_unknown
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
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [AppException] to a localized [StringResource].
 */
fun AppException.toMessage(): StringResource = when (this) {
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
    else -> Res.string.error_unknown
}

/**
 * Maps [Throwable] to a localized [StringResource].
 */
fun Throwable.toMessage(): StringResource = (this as? AppException)?.toMessage() ?: Res.string.error_unknown
