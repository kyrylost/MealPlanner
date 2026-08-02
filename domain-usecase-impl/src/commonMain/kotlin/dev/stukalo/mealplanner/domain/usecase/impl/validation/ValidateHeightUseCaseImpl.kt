package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateHeightUseCase

internal class ValidateHeightUseCaseImpl : ValidateHeightUseCase {
    override fun invoke(height: Double?): ValidationResult = when {
        height == null -> ValidationResult.Error(ValidationException.Height.Empty())
        height <= 0 || height > 300 -> ValidationResult.Error(ValidationException.Height.Invalid())
        else -> ValidationResult.Success
    }
}
