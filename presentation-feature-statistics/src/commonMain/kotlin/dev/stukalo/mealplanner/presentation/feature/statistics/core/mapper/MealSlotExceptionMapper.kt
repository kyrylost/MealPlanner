package dev.stukalo.mealplanner.presentation.feature.statistics.core.mapper

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.statistics_meal_order_error
import dev.stukalo.mealplanner.domain.model.exception.MealSlotException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [MealSlotException] to a localized [StringResource].
 */
fun MealSlotException.toMessage(): StringResource = when (this) {
    is MealSlotException.MealOrderViolation -> Res.string.statistics_meal_order_error
}
