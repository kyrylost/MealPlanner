package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult

interface ValidateWeightUseCase {
    operator fun invoke(weight: Double?): ValidationResult
}
