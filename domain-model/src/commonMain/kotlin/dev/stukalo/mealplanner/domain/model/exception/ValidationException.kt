package dev.stukalo.mealplanner.domain.model.exception

import dev.stukalo.mealplanner.core.common.exception.AppException

/**
 * Base class for all validation exceptions in the domain layer.
 */
sealed class ValidationException : AppException() {
    sealed class Name : ValidationException() {
        class TooShort : Name()

        class Empty : Name()
    }

    sealed class Date : ValidationException() {
        class Invalid : Date()

        class Future : Date()

        class Empty : Date()
    }

    sealed class Height : ValidationException() {
        class Invalid : Height()

        class Empty : Height()
    }

    sealed class Weight : ValidationException() {
        class Invalid : Weight()

        class Empty : Weight()
    }

    sealed class Gender : ValidationException() {
        class NotSelected : Gender()
    }

    sealed class ActivityLevel : ValidationException() {
        class NotSelected : ActivityLevel()
    }

    sealed class Diet : ValidationException() {
        class NotSelected : Diet()
    }

    sealed class Steps : ValidationException() {
        class Invalid : Steps()

        class Empty : Steps()
    }
}
