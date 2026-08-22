package dev.stukalo.mealplanner.presentation.feature.statistics.core.model

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import kotlinx.datetime.LocalTime

/**
 * Represents the progress of a single meal slot.
 */
internal data class MealSlotProgress(
    val id: Int,
    val type: MealTypeDomainModel,
    val startTime: LocalTime,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val isConsumed: Boolean
)
