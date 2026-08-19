package dev.stukalo.mealplanner.presentation.feature.home.screen.contract

import androidx.compose.runtime.Immutable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

@Immutable
internal data class ViewState(
    val userName: String = "",
    val currentCalories: Int = 0,
    val targetCalories: Int = 0,
    val proteins: Float = 0f,
    val proteinsTarget: Float = 0f,
    val fats: Float = 0f,
    val fatsTarget: Float = 0f,
    val carbs: Float = 0f,
    val carbsTarget: Float = 0f,
    val steps: Float = 0f,
    val stepsTarget: Float = 10000f
) : MviViewState
