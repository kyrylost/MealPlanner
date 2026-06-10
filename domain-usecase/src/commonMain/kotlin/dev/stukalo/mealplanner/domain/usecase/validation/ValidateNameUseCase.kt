package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult

interface ValidateNameUseCase {
    operator fun invoke(name: String): ValidationResult
}
