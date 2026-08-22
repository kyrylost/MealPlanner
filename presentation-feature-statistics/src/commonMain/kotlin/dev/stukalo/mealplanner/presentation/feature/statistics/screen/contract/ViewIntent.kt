package dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract

import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress
import kotlinx.datetime.LocalTime

/**
 * Represents user intentions on the Statistics screen.
 */
internal sealed interface ViewIntent : MviIntent {
    data object InitialLoad : ViewIntent

    data class OnMealConsumed(val slotId: Int) : ViewIntent

    data class OnMealClick(val meal: MealSlotProgress) : ViewIntent

    data class OnEditTimeClick(val slotId: Int, val currentTime: LocalTime) : ViewIntent

    data object OnDismissTimePickerDialog : ViewIntent

    data class OnTimeSelected(val slotId: Int, val newTime: LocalTime) : ViewIntent

    data object OnDismissDialog : ViewIntent

    data class ChangePfcCategory(val category: PfcCategory) : ViewIntent

    data class ChangeTimeInterval(val interval: StatisticsInterval) : ViewIntent

    data class ChangeWeightInterval(val interval: StatisticsInterval) : ViewIntent

    data object OnAddWeightClick : ViewIntent

    data object OnDismissAddWeightDialog : ViewIntent

    data class OnAddWeight(val weight: Double) : ViewIntent
}
