package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateNutrientProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecommendedRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.NutrientType
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

internal class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getDailyNormUseCase: GetDailyNormUseCase,
    private val getDailyProgressUseCase: GetDailyProgressUseCase,
    private val updateNutrientProgressUseCase: UpdateNutrientProgressUseCase,
    getRecommendedRecipesUseCase: GetRecommendedRecipesUseCase,
    private val clock: Clock,
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

    val recommendedRecipes = getRecommendedRecipesUseCase().cachedIn(viewModelScope)

    override val initialState = ViewState()

    init {
        onIntent(ViewIntent.InitialLoad)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.InitialLoad -> {
                viewModelScope.launch { collectUserData() }
                viewModelScope.launch { collectNutrition() }
            }
            is ViewIntent.OnRecipeClick -> {
                sendEvent(ViewEvent.NavigateToRecipeDetails(intent.recipeId))
            }
            ViewIntent.OnShowAllRecipesClick -> {
                sendEvent(ViewEvent.NavigateToRecipeSearch)
            }
            is ViewIntent.OnAddNutrient -> {
                viewModelScope.launch {
                    val nutrientType = when (intent.type) {
                        NutrientType.PROTEINS -> NutrientTypeDomainModel.PROTEIN
                        NutrientType.FATS -> NutrientTypeDomainModel.FATS
                        NutrientType.CARBS -> NutrientTypeDomainModel.CARBOHYDRATES
                    }
                    updateNutrientProgressUseCase(nutrientType, intent.amount)
                }
            }
        }
    }

    private suspend fun collectUserData() {
        getUserUseCase().collectLatest { user ->
            user?.let { userModel ->
                updateState { state ->
                    PartialStateChange.UserLoaded(userModel.name).reduce(state)
                }
            }
        }
    }

    private suspend fun collectNutrition() {
        val today = clock.todayIn(TimeZone.currentSystemDefault())
        
        combine(
            getDailyNormUseCase(),
            getDailyProgressUseCase(today)
        ) { norm, progress ->
            norm to progress
        }.collectLatest { (norm, progress) ->
            updateState { state ->
                var newState = state
                norm?.let {
                    newState = PartialStateChange.DailyNormLoaded(
                        calories = it.calories.toInt(),
                        proteins = it.proteins.toFloat(),
                        fats = it.fats.toFloat(),
                        carbs = it.carbohydrates.toFloat()
                    ).reduce(newState)
                }
                progress?.let {
                    newState = PartialStateChange.DailyProgressLoaded(
                        calories = it.consumedCalories.toInt(),
                        proteins = it.consumedProteins.toFloat(),
                        fats = it.consumedFats.toFloat(),
                        carbs = it.consumedCarbohydrates.toFloat()
                    ).reduce(newState)
                }
                newState
            }
        }
    }
}
