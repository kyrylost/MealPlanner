package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange

internal sealed interface PartialStateChange : MviPartialStateChange<ViewState> {
    override fun reduce(oldState: ViewState): ViewState

    data class FiltersChanged(val filters: FilterDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(filters = filters)
    }

    data class MinCaloriesChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(minCalories = value))
    }

    data class MaxCaloriesChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(maxCalories = value))
    }

    data class MinProteinsChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(minProteins = value))
    }

    data class MaxProteinsChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(maxProteins = value))
    }

    data class MinFatsChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(minFats = value))
    }

    data class MaxFatsChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(maxFats = value))
    }

    data class MinCarbsChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(minCarbs = value))
    }

    data class MaxCarbsChanged(val value: Int?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState =
            oldState.copy(filters = oldState.filters.copy(maxCarbs = value))
    }

    data class MealTypeToggled(val type: MealTypeDomainModel) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState {
            val currentTypes = oldState.filters.mealTypes
            val newTypes = if (currentTypes.contains(type)) currentTypes - type else currentTypes + type
            return oldState.copy(filters = oldState.filters.copy(mealTypes = newTypes))
        }
    }
}
