package dev.stukalo.mealplanner.domain.model.exception

import dev.stukalo.mealplanner.core.common.exception.AppException

/**
 * Base class for exceptions related to meal slots.
 */
sealed class MealSlotException : AppException() {
    /**
     * Thrown when the meal slot order is violated (e.g., Breakfast after Lunch).
     */
    class MealOrderViolation : MealSlotException()
}
