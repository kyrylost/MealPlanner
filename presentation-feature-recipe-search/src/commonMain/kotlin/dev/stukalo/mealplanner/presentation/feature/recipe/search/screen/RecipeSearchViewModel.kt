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
import kotlin.time.Duration.Companion.milliseconds

/**
 * ViewModel for the Recipe Search screen, responsible for handling search logic and filters.
 *
 * @property getRecipesUseCase Use case to fetch recipes based on query and filters.
 * @property getRecommendedRecipesUseCase Use case to fetch recommended recipes when no search is active.
 */
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
        safeLaunch {
            searchFlow
                .debounce(SEARCH_DEBOUNCE_MS.milliseconds)
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
                reduce(PartialStateChange.SearchQueryChange(intent.query))
                searchFlow.value = intent.query
            }
            is ViewIntent.ApplyFilters -> {
                reduce(PartialStateChange.FiltersChanged(intent.filters))
                searchWithFilters(viewState.value.searchQuery, intent.filters)
            }
            ViewIntent.OnClearFilters -> {
                reduce(PartialStateChange.SearchQueryChange(""))
                reduce(PartialStateChange.FiltersChanged(null))
                searchFlow.value = ""
                searchRecommended()
            }
            is ViewIntent.OnRecipeClick -> {
                sendEvent(ViewEvent.NavigateToRecipeDetails(intent.recipeId))
            }
            ViewIntent.OnFiltersClick -> {
                sendEvent(ViewEvent.NavigateToFilters(viewState.value.filters))
            }
            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }
        }
    }

    private fun searchRecommended() {
        safeLaunch {
            getRecommendedRecipesUseCase()
                .cachedIn(viewModelScope)
                .collectLatest { recipesFlow.value = it }
        }
    }

    private fun searchWithFilters(query: String, filters: FilterDomainModel?) {
        safeLaunch {
            getRecipesUseCase(
                calories = filters?.caloriesRange ?: DEFAULT_CALORIES_RANGE,
                carbohydrates = filters?.carbsRange ?: DEFAULT_NUTRIENT_RANGE,
                fats = filters?.fatsRange ?: DEFAULT_NUTRIENT_RANGE,
                proteins = filters?.proteinsRange ?: DEFAULT_NUTRIENT_RANGE,
                mealTypes = filters?.mealTypes ?: emptyList(),
                query = query.ifEmpty { null }
            ).cachedIn(viewModelScope)
                .collectLatest { recipesFlow.value = it }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 500L
        private val DEFAULT_CALORIES_RANGE = 0..5000
        private val DEFAULT_NUTRIENT_RANGE = 0..1000
    }
}
