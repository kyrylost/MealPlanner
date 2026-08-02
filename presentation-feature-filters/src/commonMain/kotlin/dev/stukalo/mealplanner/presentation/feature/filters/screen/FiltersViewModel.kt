package dev.stukalo.mealplanner.presentation.feature.filters.screen

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewState

class FiltersViewModel : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnInitialFilters -> {
                updateState { it.copy(filters = intent.filters) }
            }
            is ViewIntent.OnMinCaloriesChange -> {
                updateState { it.copy(filters = it.filters.copy(minCalories = intent.value)) }
            }
            is ViewIntent.OnMaxCaloriesChange -> {
                updateState { it.copy(filters = it.filters.copy(maxCalories = intent.value)) }
            }
            is ViewIntent.OnMinProteinsChange -> {
                updateState { it.copy(filters = it.filters.copy(minProteins = intent.value)) }
            }
            is ViewIntent.OnMaxProteinsChange -> {
                updateState { it.copy(filters = it.filters.copy(maxProteins = intent.value)) }
            }
            is ViewIntent.OnMinFatsChange -> {
                updateState { it.copy(filters = it.filters.copy(minFats = intent.value)) }
            }
            is ViewIntent.OnMaxFatsChange -> {
                updateState { it.copy(filters = it.filters.copy(maxFats = intent.value)) }
            }
            is ViewIntent.OnMinCarbsChange -> {
                updateState { it.copy(filters = it.filters.copy(minCarbs = intent.value)) }
            }
            is ViewIntent.OnMaxCarbsChange -> {
                updateState { it.copy(filters = it.filters.copy(maxCarbs = intent.value)) }
            }
            is ViewIntent.OnToggleMealType -> {
                val currentTypes = viewState.value.filters.mealTypes
                val newTypes =
                    if (currentTypes.contains(intent.type)) {
                        currentTypes - intent.type
                    } else {
                        currentTypes + intent.type
                    }
                updateState { it.copy(filters = it.filters.copy(mealTypes = newTypes)) }
            }
            ViewIntent.OnApplyClick -> {
                sendEvent(ViewEvent.ApplyFilters(viewState.value.filters))
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }
}
