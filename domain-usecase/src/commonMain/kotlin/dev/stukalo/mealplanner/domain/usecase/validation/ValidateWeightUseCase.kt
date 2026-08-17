package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult

interface ValidateWeightUseCase {
    operator fun invoke(weight: Double?): ValidationResult
}
