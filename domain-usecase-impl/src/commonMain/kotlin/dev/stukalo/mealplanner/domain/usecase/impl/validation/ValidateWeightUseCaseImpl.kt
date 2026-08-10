package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.model.user.UserConstants
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateWeightUseCase

internal class ValidateWeightUseCaseImpl : ValidateWeightUseCase {
    override fun invoke(weight: Double?): ValidationResult = when {
        weight == null -> ValidationResult.Error(ValidationException.Weight.Empty())
        weight < UserConstants.MIN_WEIGHT || weight > UserConstants.MAX_WEIGHT ->
            ValidationResult.Error(ValidationException.Weight.Invalid())
        else -> ValidationResult.Success
    }
}
