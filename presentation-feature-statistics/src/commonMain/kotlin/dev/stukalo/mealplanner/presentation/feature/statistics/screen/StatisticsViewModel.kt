package dev.stukalo.mealplanner.presentation.feature.statistics.screen

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
import dev.stukalo.mealplanner.presentation.feature.statistics.core.mapper.toMessage
import dev.stukalo.mealplanner.presentation.feature.statistics.core.model.MealSlotProgress
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.statistics.screen.contract.ViewState
import kotlinx.coroutines.flow.combine
import org.jetbrains.compose.resources.StringResource

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
                safeLaunch {
                    val meal = viewState.value.meals.find { it.id == intent.slotId } ?: return@safeLaunch
                    if (meal.isConsumed) return@safeLaunch

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
                reduce(PartialStateChange.SelectedMealChange(intent.meal))
            }
            ViewIntent.OnDismissDialog -> {
                reduce(PartialStateChange.SelectedMealChange(null))
            }
            is ViewIntent.ChangePfcCategory -> {
                reduce(PartialStateChange.PfcConfigChange(intent.category, viewState.value.timeInterval))
                loadStatistics()
            }
            is ViewIntent.ChangeTimeInterval -> {
                reduce(PartialStateChange.PfcConfigChange(viewState.value.pfcCategory, intent.interval))
                loadStatistics()
            }
            is ViewIntent.ChangeWeightInterval -> {
                reduce(PartialStateChange.WeightIntervalChange(intent.interval))
                loadWeightHistory()
            }
            ViewIntent.OnAddWeightClick -> {
                reduce(PartialStateChange.AddWeightDialogVisibility(isVisible = true))
            }
            ViewIntent.OnDismissAddWeightDialog -> {
                reduce(PartialStateChange.AddWeightDialogVisibility(isVisible = false))
            }
            is ViewIntent.OnAddWeight -> {
                safeLaunch {
                    saveWeightUseCase(intent.weight)
                }
            }
            is ViewIntent.OnEditTimeClick -> {
                reduce(PartialStateChange.EditTimeDialogVisibility(intent.slotId, intent.currentTime))
            }
            ViewIntent.OnDismissTimePickerDialog -> {
                reduce(PartialStateChange.EditTimeDialogVisibility(null, null))
            }
            is ViewIntent.OnTimeSelected -> {
                safeLaunch {
                    updateMealSlotTimeUseCase(intent.slotId, intent.newTime).getOrThrow()
                    reduce(PartialStateChange.EditTimeDialogVisibility(null, null))
                }
            }
        }
    }

    override fun mapThrowable(throwable: Throwable): StringResource = when (throwable) {
        is MealSlotException -> throwable.toMessage()
        else -> super.mapThrowable(throwable)
    }

    /**
     * Triggers the initial data load for the screen.
     * Fetches user info, meal schedule, daily norms, and streak information.
     */
    private fun loadData() {
        safeLaunch {
            getUserUseCase().collect { user ->
                reduce(PartialStateChange.WeightDataLoaded(viewState.value.weightData, user?.targetWeight))
            }
        }
        safeLaunch {
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
                reduce(PartialStateChange.MealsLoaded(meals))
            }
        }
        safeLaunch {
            calculateStreakUseCase().collect { streak ->
                reduce(PartialStateChange.StreakLoaded(streak))
            }
        }
        loadStatistics()
        loadWeightHistory()
    }

    /**
     * Loads nutrient statistics for the selected time interval and PFC category.
     */
    private fun loadStatistics() {
        safeLaunch {
            getStatisticsUseCase(viewState.value.timeInterval, viewState.value.pfcCategory).collect { points ->
                reduce(PartialStateChange.PfcDataLoaded(points))
            }
        }
    }

    /**
     * Loads weight history for the selected time interval.
     */
    private fun loadWeightHistory() {
        safeLaunch {
            getWeightHistoryUseCase(viewState.value.weightInterval).collect { points ->
                reduce(PartialStateChange.WeightDataLoaded(points, viewState.value.targetWeight))
            }
        }
    }
}
