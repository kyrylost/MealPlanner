package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult

interface ValidateHeightUseCase {
    operator fun invoke(height: Double?): ValidationResult
}
