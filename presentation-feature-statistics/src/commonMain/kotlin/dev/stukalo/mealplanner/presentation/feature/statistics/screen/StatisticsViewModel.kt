package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_CARB_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_FAT_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_PROTEIN_GRAM
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.TrackMealConsumedUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.CalculateStreakUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.GetStatisticsUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.GetWeightHistoryUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.SaveWeightUseCase
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * ViewModel for the Statistics screen.
 * Manages nutrient consumption statistics, weight history, and user streaks.
 *
 * @property getMealScheduleUseCase UseCase to fetch today's meal schedule.
 * @property getDailyNormUseCase UseCase to fetch the user's daily nutrient norm.
 * @property trackMealConsumedUseCase UseCase to mark a meal as consumed.
 * @property getStatisticsUseCase UseCase to fetch aggregated nutrient statistics.
 * @property getWeightHistoryUseCase UseCase to fetch weight history.
 * @property calculateStreakUseCase UseCase to calculate the current success streak.
 * @property saveWeightUseCase UseCase to save a new weight entry.
 * @property getUserUseCase UseCase to fetch user information (e.g., target weight).
 * @property clock Clock provider for today's date calculation.
 */
class StatisticsViewModel(
    private val getMealScheduleUseCase: GetMealScheduleUseCase,
    private val getDailyNormUseCase: GetDailyNormUseCase,
    private val trackMealConsumedUseCase: TrackMealConsumedUseCase,
    private val getStatisticsUseCase: GetStatisticsUseCase,
    private val getWeightHistoryUseCase: GetWeightHistoryUseCase,
    private val calculateStreakUseCase: CalculateStreakUseCase,
    private val saveWeightUseCase: SaveWeightUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val clock: kotlin.time.Clock
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    init {
        onIntent(ViewIntent.InitialLoad)
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.InitialLoad -> {
                loadData()
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
            is ViewIntent.ChangePfcCategory -> {
                updateState { it.copy(pfcCategory = intent.category) }
                loadStatistics()
            }
            is ViewIntent.ChangeTimeInterval -> {
                updateState { it.copy(timeInterval = intent.interval) }
                loadStatistics()
            }
            is ViewIntent.ChangeWeightInterval -> {
                updateState { it.copy(weightInterval = intent.interval) }
                loadWeightHistory()
            }
            ViewIntent.OnAddWeightClick -> {
                updateState { it.copy(isAddWeightDialogVisible = true) }
            }
            ViewIntent.OnDismissAddWeightDialog -> {
                updateState { it.copy(isAddWeightDialogVisible = false) }
            }
            is ViewIntent.OnAddWeight -> {
                viewModelScope.launch {
                    saveWeightUseCase(intent.weight)
                }
            }
        }
    }

    /**
     * Triggers the initial data load for the screen.
     * Fetches user info, meal schedule, daily norms, and streak information.
     */
    private fun loadData() {
        viewModelScope.launch {
            getUserUseCase().collect { user ->
                updateState { it.copy(targetWeight = user?.targetWeight) }
            }
        }
        viewModelScope.launch {
            combine(
                getMealScheduleUseCase(),
                getDailyNormUseCase()
            ) { slots, norm ->
                if (norm == null) return@combine emptyList()

                slots.map { slot ->
                    val pGrams = norm.proteins * (slot.proteinsPercentage / 100.0)
                    val fGrams = norm.fats * (slot.fatsPercentage / 100.0)
                    val cGrams = norm.carbohydrates * (slot.carbsPercentage / 100.0)
                    MealSlotProgress(
                        id = slot.id,
                        name = slot.name,
                        calories =
                        (pGrams * CALORIES_PER_PROTEIN_GRAM) + (fGrams * CALORIES_PER_FAT_GRAM) +
                            (cGrams * CALORIES_PER_CARB_GRAM),
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
        viewModelScope.launch {
            calculateStreakUseCase().collect { streak ->
                updateState { it.copy(streak = streak) }
            }
        }
        loadStatistics()
        loadWeightHistory()
    }

    /**
     * Loads nutrient statistics for the selected time interval and PFC category.
     */
    private fun loadStatistics() {
        viewModelScope.launch {
            getStatisticsUseCase(viewState.value.timeInterval, viewState.value.pfcCategory).collect { points ->
                updateState { it.copy(pfcData = points) }
            }
        }
    }

    /**
     * Loads weight history for the selected time interval.
     */
    private fun loadWeightHistory() {
        viewModelScope.launch {
            getWeightHistoryUseCase(viewState.value.weightInterval).collect { points ->
                updateState { it.copy(weightData = points) }
            }
        }
    }
}
