package dev.stukalo.mealplanner.common.core.validation

import dev.stukalo.mealplanner.common.core.exception.AppException

sealed class ValidationResult {
    data object Success : ValidationResult()

    data class Error(val exception: AppException) : ValidationResult()
}
