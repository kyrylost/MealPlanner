package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipeByIdUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.LogRecipeConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewState
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val logRecipeConsumedUseCase: LogRecipeConsumedUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.LoadRecipe -> {
                loadRecipe(intent.id)
            }
            is ViewIntent.OnLogMealClick -> {
                logMeal(intent.weight)
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }

    private fun loadRecipe(id: String) {
        viewModelScope.launch {
            updateState { PartialStateChange.Loading(true).reduce(it) }
            getRecipeByIdUseCase(id)
                .onSuccess { recipe ->
                    updateState { PartialStateChange.RecipeLoaded(recipe).reduce(it) }
                }.onFailure {
                    updateState { PartialStateChange.Loading(false).reduce(it) }
                }
        }
    }

    private fun logMeal(weight: Float) {
        val currentRecipe = viewState.value.recipe ?: return
        viewModelScope.launch {
            logRecipeConsumedUseCase(currentRecipe, weight)
            sendEvent(ViewEvent.NavigateBack)
        }
    }
}
