package dev.stukalo.mealplanner.domain.usecase.validation

import dev.stukalo.mealplanner.common.core.validation.ValidationResult

interface ValidateDateUseCase {
    operator fun invoke(date: String?): ValidationResult
}
