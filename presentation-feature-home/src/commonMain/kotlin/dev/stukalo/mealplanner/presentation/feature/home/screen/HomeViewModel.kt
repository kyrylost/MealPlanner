package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_CARB_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_FAT_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_PROTEIN_GRAM
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.NutrientType
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.time.Clock

internal class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getDailyNormUseCase: GetDailyNormUseCase,
    private val getDailyProgressUseCase: GetDailyProgressUseCase,
    private val updateDailyProgressUseCase: UpdateDailyProgressUseCase,
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

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
            is ViewIntent.OnAddNutrient -> {
                viewModelScope.launch {
                    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

                    val currentProgress = getDailyProgressUseCase(today).first()
                        ?: DailyProgressDomainModel(
                            date = today,
                            consumedCalories = 0.0,
                            consumedProteins = 0.0,
                            consumedFats = 0.0,
                            consumedCarbohydrates = 0.0
                        )

                    val newProgress = when (intent.type) {
                        NutrientType.PROTEINS -> currentProgress.copy(
                            consumedProteins = currentProgress.consumedProteins + intent.amount,
                            consumedCalories = currentProgress.consumedCalories + intent.amount * CALORIES_PER_PROTEIN_GRAM
                        )
                        NutrientType.FATS -> currentProgress.copy(
                            consumedFats = currentProgress.consumedFats + intent.amount,
                            consumedCalories = currentProgress.consumedCalories + intent.amount * CALORIES_PER_FAT_GRAM
                        )
                        NutrientType.CARBS -> currentProgress.copy(
                            consumedCarbohydrates = currentProgress.consumedCarbohydrates + intent.amount,
                            consumedCalories = currentProgress.consumedCalories + intent.amount * CALORIES_PER_CARB_GRAM
                        )
                    }
                    updateDailyProgressUseCase(newProgress)
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
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        
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
