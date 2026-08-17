package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.model.user.UserConstants
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateHeightUseCase

internal class ValidateHeightUseCaseImpl : ValidateHeightUseCase {
    override fun invoke(height: Double?): ValidationResult = when {
        height == null -> ValidationResult.Error(ValidationException.Height.Empty())
        height < UserConstants.MIN_HEIGHT || height > UserConstants.MAX_HEIGHT ->
            ValidationResult.Error(ValidationException.Height.Invalid())
        else -> ValidationResult.Success
    }
}
