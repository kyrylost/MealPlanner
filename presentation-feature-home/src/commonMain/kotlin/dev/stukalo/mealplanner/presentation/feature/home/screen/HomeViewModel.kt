package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import dev.stukalo.mealplanner.domain.usecase.health.GetGrantedHealthPermissionsUseCase
import dev.stukalo.mealplanner.domain.usecase.health.GetStepsUseCase
import dev.stukalo.mealplanner.domain.usecase.health.SyncHealthDataUseCase
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

/**
 * ViewModel for the Home screen.
 *
 * Manages user data, daily nutrition progress, and recommended recipes.
 */
internal class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getDailyNormUseCase: GetDailyNormUseCase,
    private val getDailyProgressUseCase: GetDailyProgressUseCase,
    private val updateNutrientProgressUseCase: UpdateNutrientProgressUseCase,
    private val getStepsUseCase: GetStepsUseCase,
    private val syncHealthDataUseCase: SyncHealthDataUseCase,
    private val getGrantedHealthPermissionsUseCase: GetGrantedHealthPermissionsUseCase,
    getRecommendedRecipesUseCase: GetRecommendedRecipesUseCase,
    private val clock: Clock
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    val recommendedRecipes = getRecommendedRecipesUseCase().cachedIn(viewModelScope)

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    private var lastPermissionCount = -1

    override val initialState = ViewState()

    init {
        setupStepsCollection()
        onIntent(ViewIntent.InitialLoad)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.InitialLoad -> onInitialLoad()
            is ViewIntent.OnRecipeClick -> onRecipeClick(intent.recipeId)
            ViewIntent.OnShowAllRecipesClick -> onShowAllRecipesClick()
            is ViewIntent.OnAddNutrient -> onAddNutrient(intent.type, intent.amount)
            ViewIntent.OnResume -> onResume()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setupStepsCollection() {
        viewModelScope.launch {
            refreshTrigger.flatMapLatest {
                val today = clock.todayIn(TimeZone.currentSystemDefault())
                getStepsUseCase(today)
            }.collectLatest { steps ->
                updateState { state ->
                    PartialStateChange.StepsLoaded(steps).reduce(state)
                }
            }
        }
    }

    private fun onInitialLoad() {
        viewModelScope.launch { collectUserData() }
        viewModelScope.launch { collectNutrition() }
        viewModelScope.launch { syncHealthData() }
        refreshTrigger.tryEmit(Unit)
    }

    private fun onRecipeClick(recipeId: String) {
        viewModelScope.launch {
            sendEvent(ViewEvent.NavigateToRecipeDetails(recipeId))
        }
    }

    private fun onShowAllRecipesClick() {
        viewModelScope.launch {
            sendEvent(ViewEvent.NavigateToRecipeSearch)
        }
    }

    private fun onAddNutrient(type: NutrientType, amount: Float) {
        viewModelScope.launch {
            val nutrientType =
                when (type) {
                    NutrientType.PROTEINS -> NutrientTypeDomainModel.PROTEIN
                    NutrientType.FATS -> NutrientTypeDomainModel.FATS
                    NutrientType.CARBS -> NutrientTypeDomainModel.CARBOHYDRATES
                }
            updateNutrientProgressUseCase(nutrientType, amount)
        }
    }

    private fun onResume() {
        viewModelScope.launch {
            val currentPermissions = getGrantedHealthPermissionsUseCase().size

            // Only sync and refresh steps if permissions were granted (count increased)
            // or if we haven't done an initial sync (count was -1).
            if (currentPermissions > lastPermissionCount) {
                syncHealthData()
                refreshTrigger.emit(Unit)
            }

            lastPermissionCount = currentPermissions
        }
    }

    private suspend fun syncHealthData() {
        // Silently sync whatever is permitted.
        syncHealthDataUseCase()
    }

    private suspend fun collectUserData() {
        getUserUseCase().collectLatest { user ->
            user?.let { userModel ->
                updateState { state ->
                    PartialStateChange.UserLoaded(userModel.name, userModel.stepsTarget).reduce(state)
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
                    newState =
                        PartialStateChange
                            .DailyNormLoaded(
                                calories = it.calories.toInt(),
                                proteins = it.proteins.toFloat(),
                                fats = it.fats.toFloat(),
                                carbs = it.carbohydrates.toFloat()
                            ).reduce(newState)
                }
                progress?.let {
                    newState =
                        PartialStateChange
                            .DailyProgressLoaded(
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
