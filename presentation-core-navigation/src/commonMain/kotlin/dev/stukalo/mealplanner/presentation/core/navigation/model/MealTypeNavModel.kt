package dev.stukalo.mealplanner.presentation.core.navigation.model

import kotlinx.serialization.Serializable

/**
 * Navigation model for meal types.
 * Decoupled from domain to avoid direct dependency in navigation.
 */
@Serializable
enum class MealTypeNavModel {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK,
    TEATIME
}
