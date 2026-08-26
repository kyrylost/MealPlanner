package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen

import dev.stukalo.mealplanner.domain.model.exception.RecipeException
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipeByIdUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.LogRecipeConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.recipe.common.core.mapper.toMessage
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipedetails.screen.contract.ViewState
import org.jetbrains.compose.resources.StringResource

internal class RecipeDetailsViewModel(
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

    override fun handleError(throwable: Throwable) {
        reduce(PartialStateChange.Loading(false))
        super.handleError(throwable)
    }

    override fun mapThrowable(throwable: Throwable): StringResource = when (throwable) {
        is RecipeException -> throwable.toMessage()
        else -> super.mapThrowable(throwable)
    }

    private fun loadRecipe(id: String) {
        safeLaunch {
            reduce(PartialStateChange.Loading(true))
            getRecipeByIdUseCase(id)
                .onSuccess { recipe ->
                    reduce(PartialStateChange.RecipeLoaded(recipe))
                }.onFailure {
                    handleError(it)
                }
        }
    }

    private fun logMeal(weight: Float) {
        val currentRecipe = viewState.value.recipe ?: return
        safeLaunch {
            logRecipeConsumedUseCase(currentRecipe, weight)
            sendEvent(ViewEvent.NavigateBack)
        }
    }
}
