package dev.stukalo.mealplanner.presentation.feature.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

sealed interface ViewIntent : MviIntent {
    data class OnInitialFilters(val filters: FilterDomainModel) : ViewIntent
    data class OnMinCaloriesChange(val value: Int?) : ViewIntent
    data class OnMaxCaloriesChange(val value: Int?) : ViewIntent
    data class OnMinProteinsChange(val value: Int?) : ViewIntent
    data class OnMaxProteinsChange(val value: Int?) : ViewIntent
    data class OnMinFatsChange(val value: Int?) : ViewIntent
    data class OnMaxFatsChange(val value: Int?) : ViewIntent
    data class OnMinCarbsChange(val value: Int?) : ViewIntent
    data class OnMaxCarbsChange(val value: Int?) : ViewIntent
    data class OnToggleMealType(val type: MealTypeDomainModel) : ViewIntent
    data object OnApplyClick : ViewIntent
    data object OnBackClick : ViewIntent
}
