package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecommendedRecipesUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.contract.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class RecipeSearchViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getRecommendedRecipesUseCase: GetRecommendedRecipesUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    private val recipesFlow = MutableStateFlow<PagingData<RecipeDomainModel>>(PagingData.empty())
    val recipes: Flow<PagingData<RecipeDomainModel>> = recipesFlow

    private val searchFlow = MutableStateFlow("")

    init {
        onIntent(ViewIntent.InitialLoad)
        setupSearch()
    }

    private fun setupSearch() {
        viewModelScope.launch {
            searchFlow
                .debounce(500.milliseconds)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isEmpty() && viewState.value.filters == null) {
                        searchRecommended()
                    } else {
                        searchWithFilters(query, viewState.value.filters)
                    }
                }
        }
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.InitialLoad -> {
                searchRecommended()
            }
            is ViewIntent.OnSearchQueryChange -> {
                updateState { PartialStateChange.SearchQueryChange(intent.query).reduce(it) }
                searchFlow.value = intent.query
            }
            is ViewIntent.ApplyFilters -> {
                updateState { PartialStateChange.FiltersChanged(intent.filters).reduce(it) }
                searchWithFilters(viewState.value.searchQuery, intent.filters)
            }
            ViewIntent.OnClearFilters -> {
                updateState { PartialStateChange.SearchQueryChange("").reduce(it) }
                updateState { PartialStateChange.FiltersChanged(null).reduce(it) }
                searchFlow.value = ""
                searchRecommended()
            }
            is ViewIntent.OnRecipeClick -> {
                sendEvent(ViewEvent.NavigateToRecipeDetails(intent.recipeId))
            }
            ViewIntent.OnFiltersClick -> {
                sendEvent(ViewEvent.NavigateToFilters)
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }

    private fun searchRecommended() {
        viewModelScope.launch {
            getRecommendedRecipesUseCase()
                .cachedIn(viewModelScope)
                .collectLatest { recipesFlow.value = it }
        }
    }

    private fun searchWithFilters(query: String, filters: FilterDomainModel?) {
        viewModelScope.launch {
            getRecipesUseCase(
                calories = filters?.caloriesRange ?: 0..5000,
                carbohydrates = filters?.carbsRange ?: 0..1000,
                fats = filters?.fatsRange ?: 0..1000,
                proteins = filters?.proteinsRange ?: 0..1000,
                mealTypes = filters?.mealTypes ?: emptyList(),
                query = query.ifEmpty { null }
            ).cachedIn(viewModelScope)
                .collectLatest { recipesFlow.value = it }
        }
    }
}
