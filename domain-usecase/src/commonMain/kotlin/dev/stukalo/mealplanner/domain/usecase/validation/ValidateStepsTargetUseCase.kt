package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult

interface ValidateStepsTargetUseCase {
    operator fun invoke(steps: Int?): ValidationResult
}
