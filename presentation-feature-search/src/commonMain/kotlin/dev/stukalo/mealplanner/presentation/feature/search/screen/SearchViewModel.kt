package dev.stukalo.mealplanner.presentation.feature.search.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
) : ViewModel() {

    private val _recipes = MutableStateFlow<PagingData<RecipeDomainModel>>(PagingData.empty())
    val recipes: StateFlow<PagingData<RecipeDomainModel>> = _recipes.asStateFlow()

    fun searchRecipes() {
        viewModelScope.launch {
            getRecipesUseCase(
                calories = 100..1000,
                carbohydrates = 0..100,
                fats = 0..100,
                proteins = 0..100,
                mealType = MealTypeDomainModel.LUNCH
            ).cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _recipes.value = pagingData
                }
        }
    }
}
