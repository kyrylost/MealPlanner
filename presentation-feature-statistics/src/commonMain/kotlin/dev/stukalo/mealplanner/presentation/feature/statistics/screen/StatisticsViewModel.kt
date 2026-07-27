package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.TrackMealConsumedUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val getMealScheduleUseCase: GetMealScheduleUseCase,
    private val getDailyNormUseCase: GetDailyNormUseCase,
    private val trackMealConsumedUseCase: TrackMealConsumedUseCase,
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

    override val initialState = ViewState()

    init {
        onIntent(ViewIntent.InitialLoad)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.InitialLoad -> {
                viewModelScope.launch {
                    combine(
                        getMealScheduleUseCase(),
                        getDailyNormUseCase()
                    ) { slots, norm ->
                        val dailyNorm = norm ?: DailyNormDomainModel(2000.0, 150.0, 65.0, 250.0)
                        slots.map { slot ->
                            val pGrams = dailyNorm.proteins * (slot.proteinsPercentage / 100.0)
                            val fGrams = dailyNorm.fats * (slot.fatsPercentage / 100.0)
                            val cGrams = dailyNorm.carbohydrates * (slot.carbsPercentage / 100.0)
                            MealSlotProgress(
                                id = slot.id,
                                name = slot.name,
                                calories = (pGrams * 4) + (fGrams * 9) + (cGrams * 4),
                                proteins = pGrams,
                                fats = fGrams,
                                carbohydrates = cGrams,
                                isConsumed = slot.isConsumed
                            )
                        }
                    }.collect { meals ->
                        updateState { it.copy(meals = meals) }
                    }
                }
            }
            is ViewIntent.OnMealConsumed -> {
                viewModelScope.launch {
                    val meal = viewState.value.meals.find { it.id == intent.slotId } ?: return@launch
                    if (meal.isConsumed) return@launch

                    trackMealConsumedUseCase(
                        slotId = intent.slotId,
                        calories = meal.calories,
                        proteins = meal.proteins,
                        fats = meal.fats,
                        carbohydrates = meal.carbohydrates
                    )
                }
            }
            is ViewIntent.OnMealClick -> {
                updateState { it.copy(selectedMeal = intent.meal) }
            }
            ViewIntent.OnDismissDialog -> {
                updateState { it.copy(selectedMeal = null) }
            }
        }
    }
}
