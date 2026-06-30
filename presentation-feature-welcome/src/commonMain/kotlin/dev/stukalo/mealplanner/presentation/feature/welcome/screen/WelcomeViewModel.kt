package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import dev.stukalo.mealplanner.common.core.date.formatDate
import dev.stukalo.mealplanner.common.core.date.parseDate
import dev.stukalo.mealplanner.common.core.exception.AppException
import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.common.core.validation.onValidationError
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.error_unknown
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_CARB_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_FAT_GRAM
import dev.stukalo.mealplanner.domain.model.nutrient.CALORIES_PER_PROTEIN_GRAM
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.usecase.user.SaveDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.user.SaveUserDataUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateActivityLevelUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDateUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDietUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateGenderUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateHeightUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateNameUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateWeightUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model.SnackbarType
import dev.stukalo.mealplanner.presentation.feature.welcome.mapper.toMessage
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Instant

internal class WelcomeViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateHeightUseCase: ValidateHeightUseCase,
    private val validateWeightUseCase: ValidateWeightUseCase,
    private val validateGenderUseCase: ValidateGenderUseCase,
    private val validateActivityLevelUseCase: ValidateActivityLevelUseCase,
    private val validateDietUseCase: ValidateDietUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val saveDailyNormUseCase: SaveDailyNormUseCase,
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

    override val initialState = ViewState()

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnChangeNameInputIntent -> {
                onChangeNameInput(intent)
            }

            is ViewIntent.OnChangeDateInputIntent -> {
                onChangeDateInput(intent)
            }

            is ViewIntent.OnChangeHeightInputIntent -> {
                onChangeHeightInput(intent)
            }

            is ViewIntent.OnChangeWeightInputIntent -> {
                onChangeWeightInput(intent)
            }

            is ViewIntent.OnChangeGenderInputIntent -> {
                onChangeGenderInput(intent)
            }

            is ViewIntent.OnChangeActivityLevelInputIntent -> {
                onChangeActivityLevelInput(intent)
            }

            is ViewIntent.OnChangeDietInputIntent -> {
                onChangeDietInput(intent)
            }

            ViewIntent.OnShowDatePickerIntent -> {
                updateState { PartialStateChange.ShowDatePicker(true).reduce(it) }
            }

            ViewIntent.OnHideDatePickerIntent -> {
                updateState { PartialStateChange.ShowDatePicker(false).reduce(it) }
            }

            ViewIntent.OnContinueClickIntent -> {
                validateAndNavigate()
            }

            ViewIntent.OnBackClickIntent -> {
                onBackClick()
            }
        }
    }

    private fun onChangeNameInput(
        intent: ViewIntent.OnChangeNameInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.NameInput.TextChange(
                value = intent.value,
            ).reduce(currentState)
        }

        validateNameUseCase(intent.value).onValidationError {
            updateState { currentState ->
                PartialStateChange.NameInput.Error(
                    errorMessage = it.toMessage()
                ).reduce(currentState)
            }
        }
    }

    private fun onChangeDateInput(
        intent: ViewIntent.OnChangeDateInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.DateInput.DateChange(
                date = intent.date,
            ).reduce(currentState)
        }

        val formattedDate = intent.date?.let {
            Instant.fromEpochMilliseconds(it)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date
                .formatDate()
        }.orEmpty()
        validateDateUseCase(formattedDate).onValidationError {
            updateState { currentState ->
                PartialStateChange.DateInput.Error(
                    errorMessage = it.toMessage()
                ).reduce(currentState)
            }
        }
    }

    private fun onChangeHeightInput(
        intent: ViewIntent.OnChangeHeightInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.HeightInput.ValueChange(
                value = intent.value,
            ).reduce(currentState)
        }

        validateHeightUseCase(intent.value.toDoubleOrNull()).onValidationError {
            updateState { currentState ->
                PartialStateChange.HeightInput.Error(
                    errorMessage = it.toMessage()
                ).reduce(currentState)
            }
        }
    }

    private fun onChangeWeightInput(
        intent: ViewIntent.OnChangeWeightInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.WeightInput.ValueChange(
                value = intent.value,
            ).reduce(currentState)
        }

        validateWeightUseCase(intent.value.toDoubleOrNull()).onValidationError {
            updateState { currentState ->
                PartialStateChange.WeightInput.Error(
                    errorMessage = it.toMessage()
                ).reduce(currentState)
            }
        }
    }

    private fun onChangeGenderInput(
        intent: ViewIntent.OnChangeGenderInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.GenderChange(
                gender = intent.value,
            ).reduce(currentState)
        }
    }

    private fun onChangeActivityLevelInput(
        intent: ViewIntent.OnChangeActivityLevelInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.ActivityLevelChange(
                activityLevel = intent.value,
            ).reduce(currentState)
        }
    }

    private fun onChangeDietInput(
        intent: ViewIntent.OnChangeDietInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.DietChange(
                diet = intent.value,
            ).reduce(currentState)
        }
    }

    private fun onBackClick() {
        val currentStep = viewState.value.currentStep
        if (currentStep > 1) {
            updateState { PartialStateChange.StepChange(currentStep - 1).reduce(it) }
        }
    }

    private suspend fun validateAndNavigate() {
        val state = viewState.value
        val currentStep = state.currentStep

        val validationResult = when (currentStep) {
            1 -> {
                val nameRes = validateNameUseCase(state.nameInput)
                val dateRes = validateDateUseCase(state.dateInput)
                if (nameRes is ValidationResult.Error || dateRes is ValidationResult.Error) {
                    updateState { currentState ->
                        PartialStateChange.ValidationErrors(
                            nameErrorMessage = (nameRes as? ValidationResult.Error)?.exception?.toMessage(),
                            dateErrorMessage = (dateRes as? ValidationResult.Error)?.exception?.toMessage(),
                        ).reduce(currentState)
                    }
                    ValidationResult.Error(AppException())
                } else {
                    ValidationResult.Success
                }
            }
            2 -> validateWeightUseCase(state.weightInput.toDoubleOrNull()).onValidationError {
                updateState { currentState ->
                    PartialStateChange.WeightInput.Error(it.toMessage()).reduce(currentState)
                }
            }
            3 -> validateHeightUseCase(state.heightInput.toDoubleOrNull()).onValidationError {
                updateState { currentState ->
                    PartialStateChange.HeightInput.Error(it.toMessage()).reduce(currentState)
                }
            }
            4 -> validateGenderUseCase(state.gender).onValidationError {
                sendEvent(ViewEvent.ShowSnackbar(it.toMessage(), SnackbarType.ERROR))
            }
            5 -> validateActivityLevelUseCase(state.activityLevel).onValidationError {
                sendEvent(ViewEvent.ShowSnackbar(it.toMessage(), SnackbarType.ERROR))
            }
            6 -> validateDietUseCase(state.diet).onValidationError {
                sendEvent(ViewEvent.ShowSnackbar(it.toMessage(), SnackbarType.ERROR))
            }
            else -> ValidationResult.Success
        }

        if (validationResult is ValidationResult.Success) {
            if (currentStep < 6) {
                updateState { PartialStateChange.StepChange(currentStep + 1).reduce(it) }
            } else {
                saveUserDataAndNavigate()
            }
        }
    }

    private suspend fun saveUserDataAndNavigate() {
        val state = viewState.value
        val birthDate = state.dateInput.parseDate()
        val activityLevel = state.activityLevel
        val gender = state.gender
        val diet = state.diet

        if (birthDate == null || activityLevel == null || gender == null || diet == null) {
            return
        }

        updateState { PartialStateChange.Loading(true).reduce(it) }

        val user = UserDomainModel(
            name = state.nameInput,
            birthDate = birthDate,
            height = state.heightInput.toDoubleOrNull() ?: 0.0,
            weight = state.weightInput.toDoubleOrNull() ?: 0.0,
            physicalActivity = activityLevel,
            gender = gender,
            diet = diet,
        )

        coroutineScope {
            val saveUserTask = async { saveUserDataUseCase(user) }
            val saveDailyNormTask = async {
                val dailyNorm = calculateDailyNorm(user)
                saveDailyNormUseCase(dailyNorm)
            }

            val saveUserResult = saveUserTask.await()
            val saveDailyNormResult = saveDailyNormTask.await()

            if (saveUserResult.isSuccess && saveDailyNormResult.isSuccess) {
                sendEvent(ViewEvent.NavigateToMainScreen)
            } else {
                val error = saveUserResult.exceptionOrNull() ?: saveDailyNormResult.exceptionOrNull()
                val message = error?.toMessage() ?: Res.string.error_unknown
                sendEvent(ViewEvent.ShowSnackbar(message, SnackbarType.ERROR))
            }
        }

        updateState { PartialStateChange.Loading(false).reduce(it) }
    }

    private fun calculateDailyNorm(user: UserDomainModel): DailyNormDomainModel {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        var age = today.year - user.birthDate.year
        if (today.month < user.birthDate.month ||
            (today.month == user.birthDate.month && today.day < user.birthDate.day)
        ) {
            age--
        }

        val activityCoefficient = when (user.physicalActivity) {
            ActivityLevelDomainModel.VERY_LOW -> 1.1
            ActivityLevelDomainModel.LOW -> 1.3
            ActivityLevelDomainModel.MEDIUM -> 1.5
            ActivityLevelDomainModel.HIGH -> 1.7
            ActivityLevelDomainModel.VERY_HIGH -> 1.9
        }

        var calories = if (user.gender == GenderDomainModel.MALE) {
            when (age) {
                in 0..30 -> (0.0630 * user.weight + 2.8957) * 240
                in 31..60 -> (0.0491 * user.weight + 2.4587) * 240
                else -> (0.0491 * user.weight + 1.8988) * 240
            }
        } else {
            when (age) {
                in 0..30 -> (0.0621 * user.weight + 2.0357) * 240
                in 31..60 -> (0.0342 * user.weight + 3.5377) * 240
                else -> (0.0377 * user.weight + 2.7545) * 240
            }
        }
        calories *= activityCoefficient

        val (proteinsCoefficient, fatsCoefficient, carbsCoefficient) = when (user.diet) {
            DietDomainModel.BALANCED_DIET -> Triple(0.2, 0.2, 0.6)
            DietDomainModel.WEIGHT_GAIN -> {
                calories *= 1.1
                Triple(0.28, 0.2, 0.52)
            }
            DietDomainModel.WEIGHT_LOSS -> {
                calories *= 0.8
                Triple(0.31, 0.29, 0.41)
            }
            DietDomainModel.CUTTING_DIET -> {
                calories *= 0.85
                Triple(0.5, 0.2, 0.3)
            }
        }

        return DailyNormDomainModel(
            calories = calories,
            proteins = (calories * proteinsCoefficient) / CALORIES_PER_PROTEIN_GRAM,
            fats = (calories * fatsCoefficient) / CALORIES_PER_FAT_GRAM,
            carbohydrates = (calories * carbsCoefficient) / CALORIES_PER_CARB_GRAM
        )
    }
}
