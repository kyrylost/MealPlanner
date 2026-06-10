package dev.stukalo.mealplanner.domain.model.exception

import dev.stukalo.mealplanner.common.core.exception.AppException

/**
 * Base class for all validation exceptions in the domain layer.
 */
sealed class ValidationException : AppException() {

    sealed class Name : ValidationException() {
        class TooShort : Name()
        class Empty : Name()
    }

    sealed class Email : ValidationException() {
        class InvalidFormat : Email()
        class Empty : Email()
    }

    sealed class Date : ValidationException() {
        class Invalid : Date()
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
}
