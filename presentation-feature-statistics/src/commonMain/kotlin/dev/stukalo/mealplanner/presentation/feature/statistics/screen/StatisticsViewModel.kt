package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.statistics_meal_order_error
import dev.stukalo.mealplanner.domain.model.exception.MealSlotException
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_CARB_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_FAT_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_PROTEIN_GRAM
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.TrackMealConsumedUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.UpdateMealSlotTimeUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.CalculateStreakUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.GetStatisticsUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.GetWeightHistoryUseCase
import dev.stukalo.mealplanner.domain.usecase.statistics.SaveWeightUseCase
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.PartialStateChange
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
 */
internal class StatisticsViewModel(
    private val getMealScheduleUseCase: GetMealScheduleUseCase,
    private val getDailyNormUseCase: GetDailyNormUseCase,
    private val trackMealConsumedUseCase: TrackMealConsumedUseCase,
    private val updateMealSlotTimeUseCase: UpdateMealSlotTimeUseCase,
    private val getStatisticsUseCase: GetStatisticsUseCase,
    private val getWeightHistoryUseCase: GetWeightHistoryUseCase,
    private val calculateStreakUseCase: CalculateStreakUseCase,
    private val saveWeightUseCase: SaveWeightUseCase,
    private val getUserUseCase: GetUserUseCase
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
                updateState { PartialStateChange.SelectedMealChange(intent.meal).reduce(it) }
            }
            ViewIntent.OnDismissDialog -> {
                updateState { PartialStateChange.SelectedMealChange(null).reduce(it) }
            }
            is ViewIntent.ChangePfcCategory -> {
                updateState { PartialStateChange.PfcConfigChange(intent.category, it.timeInterval).reduce(it) }
                loadStatistics()
            }
            is ViewIntent.ChangeTimeInterval -> {
                updateState { PartialStateChange.PfcConfigChange(it.pfcCategory, intent.interval).reduce(it) }
                loadStatistics()
            }
            is ViewIntent.ChangeWeightInterval -> {
                updateState { PartialStateChange.WeightIntervalChange(intent.interval).reduce(it) }
                loadWeightHistory()
            }
            ViewIntent.OnAddWeightClick -> {
                updateState { PartialStateChange.AddWeightDialogVisibility(isVisible = true).reduce(it) }
            }
            ViewIntent.OnDismissAddWeightDialog -> {
                updateState { PartialStateChange.AddWeightDialogVisibility(isVisible = false).reduce(it) }
            }
            is ViewIntent.OnAddWeight -> {
                viewModelScope.launch {
                    saveWeightUseCase(intent.weight)
                }
            }
            is ViewIntent.OnEditTimeClick -> {
                updateState {
                    PartialStateChange.EditTimeDialogVisibility(intent.slotId, intent.currentTime).reduce(it)
                }
            }
            ViewIntent.OnDismissTimePickerDialog -> {
                updateState { PartialStateChange.EditTimeDialogVisibility(null, null).reduce(it) }
            }
            is ViewIntent.OnTimeSelected -> {
                viewModelScope.launch {
                    val result = updateMealSlotTimeUseCase(intent.slotId, intent.newTime)
                    if (result.isSuccess) {
                        updateState { PartialStateChange.EditTimeDialogVisibility(null, null).reduce(it) }
                    } else {
                        val error = result.exceptionOrNull()
                        if (error is MealSlotException.MealOrderViolation) {
                            sendEvent(ViewEvent.ShowError(Res.string.statistics_meal_order_error))
                        }
                    }
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
                updateState { PartialStateChange.WeightDataLoaded(it.weightData, user?.targetWeight).reduce(it) }
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
                        type = slot.mealType,
                        startTime = slot.startTime,
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
                updateState { PartialStateChange.MealsLoaded(meals).reduce(it) }
            }
        }
        viewModelScope.launch {
            calculateStreakUseCase().collect { streak ->
                updateState { PartialStateChange.StreakLoaded(streak).reduce(it) }
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
                updateState { PartialStateChange.PfcDataLoaded(points).reduce(it) }
            }
        }
    }

    /**
     * Loads weight history for the selected time interval.
     */
    private fun loadWeightHistory() {
        viewModelScope.launch {
            getWeightHistoryUseCase(viewState.value.weightInterval).collect { points ->
                updateState { PartialStateChange.WeightDataLoaded(points, it.targetWeight).reduce(it) }
            }
        }
    }
}
