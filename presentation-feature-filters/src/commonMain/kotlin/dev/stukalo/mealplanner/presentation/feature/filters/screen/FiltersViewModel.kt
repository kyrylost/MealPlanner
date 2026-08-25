package dev.stukalo.mealplanner.presentation.feature.filters.screen

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewState

internal class FiltersViewModel : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnInitialFilters -> {
                reduce(PartialStateChange.FiltersChanged(intent.filters))
            }
            is ViewIntent.OnMinCaloriesChange -> {
                reduce(PartialStateChange.MinCaloriesChanged(intent.value))
            }
            is ViewIntent.OnMaxCaloriesChange -> {
                reduce(PartialStateChange.MaxCaloriesChanged(intent.value))
            }
            is ViewIntent.OnMinProteinsChange -> {
                reduce(PartialStateChange.MinProteinsChanged(intent.value))
            }
            is ViewIntent.OnMaxProteinsChange -> {
                reduce(PartialStateChange.MaxProteinsChanged(intent.value))
            }
            is ViewIntent.OnMinFatsChange -> {
                reduce(PartialStateChange.MinFatsChanged(intent.value))
            }
            is ViewIntent.OnMaxFatsChange -> {
                reduce(PartialStateChange.MaxFatsChanged(intent.value))
            }
            is ViewIntent.OnMinCarbsChange -> {
                reduce(PartialStateChange.MinCarbsChanged(intent.value))
            }
            is ViewIntent.OnMaxCarbsChange -> {
                reduce(PartialStateChange.MaxCarbsChanged(intent.value))
            }
            is ViewIntent.OnToggleMealType -> {
                reduce(PartialStateChange.MealTypeToggled(intent.type))
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
