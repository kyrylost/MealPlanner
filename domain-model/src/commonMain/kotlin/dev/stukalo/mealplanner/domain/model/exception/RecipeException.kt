package dev.stukalo.mealplanner.domain.model.exception

import dev.stukalo.mealplanner.core.common.exception.AppException

/**
 * Base class for exceptions related to recipes.
 */
sealed class RecipeException : AppException() {
    /**
     * Thrown when a recipe is not found.
     */
    class RecipeNotFound : RecipeException()
}
