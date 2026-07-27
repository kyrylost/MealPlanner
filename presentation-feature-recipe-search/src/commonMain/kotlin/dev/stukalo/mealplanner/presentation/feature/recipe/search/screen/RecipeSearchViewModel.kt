package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecommendedRecipesUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeSearchViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getRecommendedRecipesUseCase: GetRecommendedRecipesUseCase,
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

    override val initialState = ViewState()

    private val _recipes = MutableStateFlow<PagingData<RecipeDomainModel>>(PagingData.empty())
    val recipes: Flow<PagingData<RecipeDomainModel>> = _recipes

    init {
        onIntent(ViewIntent.InitialLoad)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.InitialLoad -> {
                searchRecommended()
            }
            is ViewIntent.ApplyFilters -> {
                updateState { it.copy(filters = intent.filters) }
                searchWithFilters(intent.filters)
            }
            is ViewIntent.OnRecipeClick -> {
                sendEvent(ViewEvent.NavigateToRecipeDetails(intent.recipeId))
            }
            ViewIntent.OnFiltersClick -> {
                sendEvent(ViewEvent.NavigateToFilters)
            }
        }
    }

    private fun searchRecommended() {
        viewModelScope.launch {
            getRecommendedRecipesUseCase()
                .cachedIn(viewModelScope)
                .collectLatest { _recipes.value = it }
        }
    }

    private fun searchWithFilters(filters: FilterDomainModel) {
        viewModelScope.launch {
            getRecipesUseCase(
                calories = filters.caloriesRange ?: 0..2000,
                carbohydrates = filters.carbsRange ?: 0..500,
                fats = filters.fatsRange ?: 0..500,
                proteins = filters.proteinsRange ?: 0..500,
                mealTypes = filters.mealTypes
            ).cachedIn(viewModelScope)
                .collectLatest { _recipes.value = it }
        }
    }
}
