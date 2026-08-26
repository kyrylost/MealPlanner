package dev.stukalo.mealplanner.presentation.feature.recipe.filters.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent

/**
 * Defines all possible user intents for the Filters feature.
 */
internal sealed interface ViewIntent : MviIntent {
    /** Sets initial filters on screen start. */
    data class OnInitialFilters(val filters: FilterDomainModel) : ViewIntent

    /** Triggered when the minimum calories value changes. */
    data class OnMinCaloriesChange(val value: Int?) : ViewIntent

    /** Triggered when the maximum calories value changes. */
    data class OnMaxCaloriesChange(val value: Int?) : ViewIntent

    /** Triggered when the minimum proteins value changes. */
    data class OnMinProteinsChange(val value: Int?) : ViewIntent

    /** Triggered when the maximum proteins value changes. */
    data class OnMaxProteinsChange(val value: Int?) : ViewIntent

    /** Triggered when the minimum fats value changes. */
    data class OnMinFatsChange(val value: Int?) : ViewIntent

    /** Triggered when the maximum fats value changes. */
    data class OnMaxFatsChange(val value: Int?) : ViewIntent

    /** Triggered when the minimum carbs value changes. */
    data class OnMinCarbsChange(val value: Int?) : ViewIntent

    /** Triggered when the maximum carbs value changes. */
    data class OnMaxCarbsChange(val value: Int?) : ViewIntent

    /** Triggered when a meal type is toggled. */
    data class OnToggleMealType(val type: MealTypeDomainModel) : ViewIntent

    /** Triggered when the apply button is clicked. */
    data object OnApplyClick : ViewIntent

    /** Triggered when the back button is clicked. */
    data object OnBackClick : ViewIntent
}
