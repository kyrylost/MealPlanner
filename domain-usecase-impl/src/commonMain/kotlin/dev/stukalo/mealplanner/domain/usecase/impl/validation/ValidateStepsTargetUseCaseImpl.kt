package dev.stukalo.mealplanner.domain.usecase.impl.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.domain.model.exception.ValidationException
import dev.stukalo.mealplanner.domain.model.user.UserConstants
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateStepsTargetUseCase

internal class ValidateStepsTargetUseCaseImpl : ValidateStepsTargetUseCase {
    override fun invoke(steps: Int?): ValidationResult = when {
        steps == null -> ValidationResult.Error(ValidationException.Steps.Empty())
        steps < UserConstants.MIN_STEPS_TARGET || steps > UserConstants.MAX_STEPS_TARGET ->
            ValidationResult.Error(ValidationException.Steps.Invalid())
        else -> ValidationResult.Success
    }
}
