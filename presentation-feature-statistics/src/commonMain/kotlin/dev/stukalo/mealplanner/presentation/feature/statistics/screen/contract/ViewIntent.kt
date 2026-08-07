package dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract

import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

/**
 * Represents user intentions on the Statistics screen.
 */
sealed interface ViewIntent : MviIntent {
    data object InitialLoad : ViewIntent

    data class OnMealConsumed(val slotId: Int) : ViewIntent

    data class OnMealClick(val meal: MealSlotProgress) : ViewIntent

    data object OnDismissDialog : ViewIntent

    data class ChangePfcCategory(val category: PfcCategory) : ViewIntent

    data class ChangeTimeInterval(val interval: StatisticsInterval) : ViewIntent

    data class ChangeWeightInterval(val interval: StatisticsInterval) : ViewIntent

    data object OnAddWeightClick : ViewIntent

    data object OnDismissAddWeightDialog : ViewIntent

    data class OnAddWeight(val weight: Double) : ViewIntent
}
