package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import dev.stukalo.mealplanner.core.common.date.formatDate
import dev.stukalo.mealplanner.core.common.date.parseDate
import dev.stukalo.mealplanner.core.common.exception.AppException
import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.core.common.validation.onValidationError
import dev.stukalo.mealplanner.domain.model.user.UserConstants
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.usecase.user.CalculateDailyNormUseCase
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
import dev.stukalo.mealplanner.presentation.core.ui.mapper.toMessage
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/**
 * ViewModel for the Welcome (Onboarding) flow.
 * Handles user profile data entry, validation, and saving.
 */
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
    private val calculateDailyNormUseCase: CalculateDailyNormUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState = ViewState()

    init {
        val weight = UserConstants.DEFAULT_WEIGHT.toInt().toString()
        val height = UserConstants.DEFAULT_HEIGHT.toInt().toString()
        reduce(PartialStateChange.InitialSetup(weight, height))
    }

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

            is ViewIntent.OnChangeTargetWeightInputIntent -> {
                onChangeTargetWeightInput(intent)
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
                reduce(PartialStateChange.ShowDatePicker(true))
            }

            ViewIntent.OnHideDatePickerIntent -> {
                reduce(PartialStateChange.ShowDatePicker(false))
            }

            ViewIntent.OnContinueClickIntent -> {
                validateAndNavigate()
            }

            ViewIntent.OnBackClickIntent -> {
                onBackClick()
            }
        }
    }

    override fun handleError(throwable: Throwable) {
        reduce(PartialStateChange.Loading(false))
        super.handleError(throwable)
    }

    private fun onChangeNameInput(intent: ViewIntent.OnChangeNameInputIntent) {
        reduce(PartialStateChange.NameInput.TextChange(value = intent.value))

        validateNameUseCase(intent.value).onValidationError {
            reduce(PartialStateChange.NameInput.Error(errorMessage = it.toMessage()))
        }
    }

    private fun onChangeDateInput(intent: ViewIntent.OnChangeDateInputIntent) {
        reduce(PartialStateChange.DateInput.DateChange(date = intent.date))

        val formattedDate =
            intent.date
                ?.let {
                    Instant
                        .fromEpochMilliseconds(it)
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .date
                        .formatDate()
                }.orEmpty()
        validateDateUseCase(formattedDate).onValidationError {
            reduce(PartialStateChange.DateInput.Error(errorMessage = it.toMessage()))
        }
    }

    private fun onChangeHeightInput(intent: ViewIntent.OnChangeHeightInputIntent) {
        reduce(PartialStateChange.HeightInput.ValueChange(value = intent.value))

        validateHeightUseCase(intent.value.toDoubleOrNull()).onValidationError {
            reduce(PartialStateChange.HeightInput.Error(errorMessage = it.toMessage()))
        }
    }

    private fun onChangeWeightInput(intent: ViewIntent.OnChangeWeightInputIntent) {
        reduce(PartialStateChange.WeightInput.ValueChange(value = intent.value))

        validateWeightUseCase(intent.value.toDoubleOrNull()).onValidationError {
            reduce(PartialStateChange.WeightInput.Error(errorMessage = it.toMessage()))
        }
    }

    private fun onChangeTargetWeightInput(intent: ViewIntent.OnChangeTargetWeightInputIntent) {
        reduce(PartialStateChange.TargetWeightInput.ValueChange(value = intent.value))

        validateWeightUseCase(intent.value.toDoubleOrNull()).onValidationError {
            reduce(PartialStateChange.TargetWeightInput.Error(errorMessage = it.toMessage()))
        }
    }

    private fun onChangeGenderInput(intent: ViewIntent.OnChangeGenderInputIntent) {
        reduce(PartialStateChange.GenderChange(gender = intent.value))
    }

    private fun onChangeActivityLevelInput(intent: ViewIntent.OnChangeActivityLevelInputIntent) {
        reduce(PartialStateChange.ActivityLevelChange(activityLevel = intent.value))
    }

    private fun onChangeDietInput(intent: ViewIntent.OnChangeDietInputIntent) {
        reduce(PartialStateChange.DietChange(diet = intent.value))
    }

    private fun onBackClick() {
        val currentStep = viewState.value.currentStep
        if (currentStep > 1) {
            reduce(PartialStateChange.StepChange(currentStep - 1))
        }
    }

    private suspend fun validateAndNavigate() {
        val state = viewState.value
        val currentStep = state.currentStep

        val validationResult =
            when (currentStep) {
                1 -> {
                    val nameRes = validateNameUseCase(state.nameInput)
                    val dateRes = validateDateUseCase(state.dateInput)
                    if (nameRes is ValidationResult.Error || dateRes is ValidationResult.Error) {
                        reduce(
                            PartialStateChange.ValidationErrors(
                                nameErrorMessage = (nameRes as? ValidationResult.Error)?.exception?.toMessage(),
                                dateErrorMessage = (dateRes as? ValidationResult.Error)?.exception?.toMessage()
                            )
                        )
                        ValidationResult.Error(AppException())
                    } else {
                        ValidationResult.Success
                    }
                }
                2 ->
                    validateWeightUseCase(state.weightInput.toDoubleOrNull()).onValidationError {
                        reduce(PartialStateChange.WeightInput.Error(it.toMessage()))
                    }
                3 ->
                    validateWeightUseCase(state.targetWeightInput.toDoubleOrNull()).onValidationError {
                        reduce(PartialStateChange.TargetWeightInput.Error(it.toMessage()))
                    }
                4 ->
                    validateHeightUseCase(state.heightInput.toDoubleOrNull()).onValidationError {
                        reduce(PartialStateChange.HeightInput.Error(it.toMessage()))
                    }
                5 ->
                    validateGenderUseCase(state.gender).onValidationError {
                        handleError(it)
                    }
                6 ->
                    validateActivityLevelUseCase(state.activityLevel).onValidationError {
                        handleError(it)
                    }
                7 ->
                    validateDietUseCase(state.diet).onValidationError {
                        handleError(it)
                    }
                else -> ValidationResult.Success
            }

        if (validationResult is ValidationResult.Success) {
            if (currentStep < 7) {
                reduce(PartialStateChange.StepChange(currentStep + 1))
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

        reduce(PartialStateChange.Loading(true))

        val user =
            UserDomainModel(
                name = state.nameInput,
                birthDate = birthDate,
                height = state.heightInput.toDoubleOrNull() ?: 0.0,
                weight = state.weightInput.toDoubleOrNull() ?: 0.0,
                targetWeight = state.targetWeightInput.toDoubleOrNull() ?: 0.0,
                physicalActivity = activityLevel,
                gender = gender,
                diet = diet,
                stepsTarget = UserConstants.DEFAULT_STEPS_TARGET
            )

        coroutineScope {
            val saveUserTask = async { saveUserDataUseCase(user) }
            val saveDailyNormTask =
                async {
                    val dailyNorm = calculateDailyNormUseCase(user)
                    saveDailyNormUseCase(dailyNorm)
                }

            val saveUserResult = saveUserTask.await()
            val saveDailyNormResult = saveDailyNormTask.await()

            if (saveUserResult.isSuccess && saveDailyNormResult.isSuccess) {
                sendEvent(ViewEvent.NavigateToMainScreen)
            } else {
                val error = saveUserResult.exceptionOrNull() ?: saveDailyNormResult.exceptionOrNull()
                error?.let { handleError(it) }
            }
        }

        reduce(PartialStateChange.Loading(false))
    }
}
