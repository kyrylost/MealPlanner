package dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract

import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsPoint
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress
import kotlinx.datetime.LocalTime

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    data class MealsLoaded(val meals: List<MealSlotProgress>) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(meals = meals)
    }

    data class SelectedMealChange(val meal: MealSlotProgress?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(selectedMeal = meal)
    }

    data class StreakLoaded(val streak: Int) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(streak = streak)
    }

    data class PfcDataLoaded(val data: List<StatisticsPoint>) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(pfcData = data)
    }

    data class WeightDataLoaded(val data: List<StatisticsPoint>, val targetWeight: Double?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            weightData = data,
            targetWeight = targetWeight
        )
    }

    data class PfcConfigChange(val category: PfcCategory, val interval: StatisticsInterval) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            pfcCategory = category,
            timeInterval = interval
        )
    }

    data class WeightIntervalChange(val interval: StatisticsInterval) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(weightInterval = interval)
    }

    data class AddWeightDialogVisibility(val isVisible: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isAddWeightDialogVisible = isVisible)
    }

    data class EditTimeDialogVisibility(val slotId: Int?, val currentTime: LocalTime?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            editingSlotId = slotId,
            editingSlotTime = currentTime
        )
    }
}
