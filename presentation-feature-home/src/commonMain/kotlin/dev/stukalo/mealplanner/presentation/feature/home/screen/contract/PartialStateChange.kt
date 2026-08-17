package dev.stukalo.mealplanner.presentation.feature.home.screen.contract

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

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

    data class Loading(val isLoading: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isLoading = isLoading)
    }

    data class StepsLoaded(val steps: Int) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(steps = steps.toFloat())
    }
}
