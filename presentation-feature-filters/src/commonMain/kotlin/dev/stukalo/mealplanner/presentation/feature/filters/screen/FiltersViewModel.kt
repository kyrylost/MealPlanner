package dev.stukalo.mealplanner.presentation.feature.filters.screen

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewState

class FiltersViewModel : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnInitialFilters -> {
                updateState { PartialStateChange.FiltersChanged(intent.filters).reduce(it) }
            }
            is ViewIntent.OnMinCaloriesChange -> {
                updateState {
                    PartialStateChange.FiltersChanged(it.filters.copy(minCalories = intent.value)).reduce(it)
                }
            }
            is ViewIntent.OnMaxCaloriesChange -> {
                updateState {
                    PartialStateChange.FiltersChanged(it.filters.copy(maxCalories = intent.value)).reduce(it)
                }
            }
            is ViewIntent.OnMinProteinsChange -> {
                updateState {
                    PartialStateChange.FiltersChanged(it.filters.copy(minProteins = intent.value)).reduce(it)
                }
            }
            is ViewIntent.OnMaxProteinsChange -> {
                updateState {
                    PartialStateChange.FiltersChanged(it.filters.copy(maxProteins = intent.value)).reduce(it)
                }
            }
            is ViewIntent.OnMinFatsChange -> {
                updateState { PartialStateChange.FiltersChanged(it.filters.copy(minFats = intent.value)).reduce(it) }
            }
            is ViewIntent.OnMaxFatsChange -> {
                updateState { PartialStateChange.FiltersChanged(it.filters.copy(maxFats = intent.value)).reduce(it) }
            }
            is ViewIntent.OnMinCarbsChange -> {
                updateState { PartialStateChange.FiltersChanged(it.filters.copy(minCarbs = intent.value)).reduce(it) }
            }
            is ViewIntent.OnMaxCarbsChange -> {
                updateState { PartialStateChange.FiltersChanged(it.filters.copy(maxCarbs = intent.value)).reduce(it) }
            }
            is ViewIntent.OnToggleMealType -> {
                val currentTypes = viewState.value.filters.mealTypes
                val newTypes =
                    if (currentTypes.contains(intent.type)) {
                        currentTypes - intent.type
                    } else {
                        currentTypes + intent.type
                    }
                updateState { PartialStateChange.FiltersChanged(it.filters.copy(mealTypes = newTypes)).reduce(it) }
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
