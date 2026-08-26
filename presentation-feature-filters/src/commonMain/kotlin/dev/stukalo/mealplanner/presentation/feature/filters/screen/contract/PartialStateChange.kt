package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

/**
 * Defines all possible partial state changes for the Filters feature.
 * These changes are used to reduce the current [ViewState] into a new one.
 */
internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    /**
     * Updates the entire filter set.
     */
    data class FiltersChanged(val filters: FilterDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(filters = filters)
    }

    /**
     * Grouped changes for nutrient filters (min/max values).
     */
    sealed interface NutrientChange : PartialStateChange {
        data class MinCaloriesChanged(val value: Int?) : NutrientChange
        data class MaxCaloriesChanged(val value: Int?) : NutrientChange
        data class MinProteinsChanged(val value: Int?) : NutrientChange
        data class MaxProteinsChanged(val value: Int?) : NutrientChange
        data class MinFatsChanged(val value: Int?) : NutrientChange
        data class MaxFatsChanged(val value: Int?) : NutrientChange
        data class MinCarbsChanged(val value: Int?) : NutrientChange
        data class MaxCarbsChanged(val value: Int?) : NutrientChange

        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is MinCaloriesChanged -> oldState.copy(filters = oldState.filters.copy(minCalories = value))
            is MaxCaloriesChanged -> oldState.copy(filters = oldState.filters.copy(maxCalories = value))
            is MinProteinsChanged -> oldState.copy(filters = oldState.filters.copy(minProteins = value))
            is MaxProteinsChanged -> oldState.copy(filters = oldState.filters.copy(maxProteins = value))
            is MinFatsChanged -> oldState.copy(filters = oldState.filters.copy(minFats = value))
            is MaxFatsChanged -> oldState.copy(filters = oldState.filters.copy(maxFats = value))
            is MinCarbsChanged -> oldState.copy(filters = oldState.filters.copy(minCarbs = value))
            is MaxCarbsChanged -> oldState.copy(filters = oldState.filters.copy(maxCarbs = value))
        }
    }

    /**
     * Toggles a meal type in the filter set.
     */
    data class MealTypeToggled(val type: MealTypeDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState {
            val currentTypes = oldState.filters.mealTypes
            val newTypes = if (currentTypes.contains(type)) currentTypes - type else currentTypes + type
            return oldState.copy(filters = oldState.filters.copy(mealTypes = newTypes))
        }
    }
}
