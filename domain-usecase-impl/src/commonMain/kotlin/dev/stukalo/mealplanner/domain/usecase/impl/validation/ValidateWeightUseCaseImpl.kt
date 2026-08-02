package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateWeightUseCase

internal class ValidateWeightUseCaseImpl : ValidateWeightUseCase {
    override fun invoke(weight: Double?): ValidationResult = when {
        weight == null -> ValidationResult.Error(ValidationException.Weight.Empty())
        weight <= 0 || weight > 500 -> ValidationResult.Error(ValidationException.Weight.Invalid())
        else -> ValidationResult.Success
    }
}
