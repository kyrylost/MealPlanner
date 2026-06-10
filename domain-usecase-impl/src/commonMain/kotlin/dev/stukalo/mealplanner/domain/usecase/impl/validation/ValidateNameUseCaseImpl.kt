package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateNameUseCase

internal class ValidateNameUseCaseImpl : ValidateNameUseCase {
    override fun invoke(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult.Error(ValidationException.Name.Empty())
            name.length < 2 -> ValidationResult.Error(ValidationException.Name.TooShort())
            else -> ValidationResult.Success
        }
    }
}
