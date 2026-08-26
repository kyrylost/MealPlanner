package dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen.contract.ViewState

/**
 * ViewModel for the Recipe Filters feature.
 * Manages the state of recipe filters and processes user intents.
 *
 * @param initialFilters The filters to initialize the state with.
 */
internal class RecipeFiltersViewModel(initialFilters: FilterDomainModel?) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState(filters = initialFilters ?: FilterDomainModel())

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnInitialFilters -> {
                reduce(PartialStateChange.FiltersChanged(intent.filters))
            }
            is ViewIntent.OnMinCaloriesChange -> {
                reduce(PartialStateChange.NutrientChange.MinCaloriesChanged(intent.value))
            }
            is ViewIntent.OnMaxCaloriesChange -> {
                reduce(PartialStateChange.NutrientChange.MaxCaloriesChanged(intent.value))
            }
            is ViewIntent.OnMinProteinsChange -> {
                reduce(PartialStateChange.NutrientChange.MinProteinsChanged(intent.value))
            }
            is ViewIntent.OnMaxProteinsChange -> {
                reduce(PartialStateChange.NutrientChange.MaxProteinsChanged(intent.value))
            }
            is ViewIntent.OnMinFatsChange -> {
                reduce(PartialStateChange.NutrientChange.MinFatsChanged(intent.value))
            }
            is ViewIntent.OnMaxFatsChange -> {
                reduce(PartialStateChange.NutrientChange.MaxFatsChanged(intent.value))
            }
            is ViewIntent.OnMinCarbsChange -> {
                reduce(PartialStateChange.NutrientChange.MinCarbsChanged(intent.value))
            }
            is ViewIntent.OnMaxCarbsChange -> {
                reduce(PartialStateChange.NutrientChange.MaxCarbsChanged(intent.value))
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
