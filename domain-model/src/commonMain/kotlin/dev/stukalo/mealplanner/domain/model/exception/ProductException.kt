package dev.stukalo.mealplanner.domain.model.exception

import dev.stukalo.mealplanner.core.common.exception.AppException

sealed class ProductException : AppException() {
    class ProductNotFound : ProductException()
}
