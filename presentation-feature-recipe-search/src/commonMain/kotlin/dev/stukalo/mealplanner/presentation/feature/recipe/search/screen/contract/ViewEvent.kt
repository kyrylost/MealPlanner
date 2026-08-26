package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract

import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent

/**
 * Represents one-time events for the Recipe Search screen, such as navigation.
 */
internal sealed interface ViewEvent : MviSingleEvent {
    /**
     * Navigates to the details of a specific recipe.
     *
     * @property recipeId The unique identifier of the recipe.
     */
    data class NavigateToRecipeDetails(val recipeId: String) : ViewEvent

    /**
     * Navigates to the filters screen.
     *
     * @property filters The current filters to be applied or modified.
     */
    data class NavigateToFilters(val filters: FilterDomainModel?) : ViewEvent

    /**
     * Navigates back to the previous screen.
     */
    data object NavigateBack : ViewEvent
}
