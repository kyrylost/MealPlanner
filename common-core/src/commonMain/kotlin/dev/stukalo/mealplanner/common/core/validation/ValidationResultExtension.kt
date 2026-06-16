package dev.stukalo.mealplanner.common.core.validation

import dev.stukalo.mealplanner.common.core.exception.AppException

inline fun ValidationResult.onValidationSuccess(action: () -> Unit): ValidationResult {
    if (this is ValidationResult.Success) {
        action()
    }
    return this
}

inline fun ValidationResult.onValidationError(action: (AppException) -> Unit): ValidationResult {
    if (this is ValidationResult.Error) {
        action(this.exception)
    }
    return this
}