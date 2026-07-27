package dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

data class ViewState(
    val meals: List<MealSlotProgress> = emptyList(),
    val selectedMeal: MealSlotProgress? = null,
    val isLoading: Boolean = false
) : MviViewState

data class MealSlotProgress(
    val id: Int,
    val name: String,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val isConsumed: Boolean
)
