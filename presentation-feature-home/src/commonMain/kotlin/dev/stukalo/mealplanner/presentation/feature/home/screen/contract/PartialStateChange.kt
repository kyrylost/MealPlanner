package dev.stukalo.mealplanner.presentation.feature.home.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    data class UserLoaded(val userName: String, val stepsTarget: Int) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            userName = userName,
            stepsTarget = stepsTarget.toFloat()
        )
    }

    data class DailyNormLoaded(val calories: Int, val proteins: Float, val fats: Float, val carbs: Float) :
        PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            targetCalories = calories,
            proteinsTarget = proteins,
            fatsTarget = fats,
            carbsTarget = carbs
        )
    }

    data class DailyProgressLoaded(val calories: Int, val proteins: Float, val fats: Float, val carbs: Float) :
        PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            currentCalories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs
        )
    }

    data class StepsLoaded(val steps: Int) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(steps = steps.toFloat())
    }
}
