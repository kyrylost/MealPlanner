package dev.stukalo.mealplanner.core.common.validation

import dev.stukalo.mealplanner.core.common.exception.AppException

sealed class ValidationResult {
    data object Success : ValidationResult()

    data class Error(val exception: AppException) : ValidationResult()
}
