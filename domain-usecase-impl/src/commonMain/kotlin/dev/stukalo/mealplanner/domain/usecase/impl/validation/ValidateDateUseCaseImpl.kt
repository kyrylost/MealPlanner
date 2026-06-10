package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDateUseCase

internal class ValidateDateUseCaseImpl : ValidateDateUseCase {
    override fun invoke(date: String?): ValidationResult {
        return if (date.isNullOrBlank()) {
            ValidationResult.Error(ValidationException.Date.Empty())
        } else {
            ValidationResult.Success
        }
    }
}
