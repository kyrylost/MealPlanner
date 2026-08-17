package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.core.common.validation.ValidationResult

interface ValidateDateUseCase {
    operator fun invoke(date: String?): ValidationResult
}
