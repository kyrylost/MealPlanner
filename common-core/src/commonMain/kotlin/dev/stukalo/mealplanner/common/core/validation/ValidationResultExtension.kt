package dev.stukalo.mealplanner.common.core.validation

import dev.stukalo.mealplanner.common.core.exception.AppException

inline fun ValidationResult.onSuccess(action: () -> Unit): ValidationResult {
    if (this is ValidationResult.Success) {
        action()
    }
    return this
}

inline fun ValidationResult.onError(action: (AppException) -> Unit): ValidationResult {
    if (this is ValidationResult.Error) {
        action(this.exception)
    }
    return this
}