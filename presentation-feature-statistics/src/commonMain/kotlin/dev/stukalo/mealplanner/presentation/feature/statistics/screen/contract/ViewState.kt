package dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract

import dev.stukalo.mealplanner.domain.model.statistics.PfcCategory
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsInterval
import dev.stukalo.mealplanner.domain.model.statistics.StatisticsPoint
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress

/**
 * Represents the state of the Statistics screen.
 *
 * @property meals List of today's meal slots with their progress.
 * @property selectedMeal The meal currently selected for detailed view.
 * @property streak Current consecutive days of meeting nutritional goals.
 * @property pfcData Data points for the PFC (Proteins, Fats, Carbohydrates) chart.
 * @property weightData Data points for the weight history chart.
 * @property targetWeight The user's goal weight.
 * @property pfcCategory The selected nutrient category for the PFC chart.
 * @property timeInterval The selected time interval for the PFC chart.
 * @property weightInterval The selected time interval for the weight history chart.
 * @property isAddWeightDialogVisible Whether the "Add Weight" dialog is currently shown.
 */
data class ViewState(
    val meals: List<MealSlotProgress> = emptyList(),
    val selectedMeal: MealSlotProgress? = null,
    val streak: Int = 0,
    val pfcData: List<StatisticsPoint> = emptyList(),
    val weightData: List<StatisticsPoint> = emptyList(),
    val targetWeight: Double? = null,
    val pfcCategory: PfcCategory = PfcCategory.CALORIES,
    val timeInterval: StatisticsInterval = StatisticsInterval.WEEK,
    val weightInterval: StatisticsInterval = StatisticsInterval.WEEK,
    val isAddWeightDialogVisible: Boolean = false
) : MviViewState
