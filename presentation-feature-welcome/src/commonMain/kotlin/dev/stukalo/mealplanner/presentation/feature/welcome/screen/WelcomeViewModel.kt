package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import dev.stukalo.mealplanner.common.core.date.formatDate
import dev.stukalo.mealplanner.common.core.exception.AppException
import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.common.core.validation.onError
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
import java.util.Date

internal class WelcomeViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateHeightUseCase: ValidateHeightUseCase,
    private val validateWeightUseCase: ValidateWeightUseCase,
    private val validateGenderUseCase: ValidateGenderUseCase,
    private val validateActivityLevelUseCase: ValidateActivityLevelUseCase,
    private val validateDietUseCase: ValidateDietUseCase,
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

        validateNameUseCase(intent.value).onError {
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

        val formattedDate = intent.date?.let { Date(it).formatDate() }.orEmpty()
        validateDateUseCase(formattedDate).onError {
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

        validateHeightUseCase(intent.value.toDoubleOrNull()).onError {
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

        validateWeightUseCase(intent.value.toDoubleOrNull()).onError {
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
            PartialStateChange.GenderInput.SelectionChange(
                gender = intent.value,
            ).reduce(currentState)
        }
    }

    private fun onChangeActivityLevelInput(
        intent: ViewIntent.OnChangeActivityLevelInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.ActivityLevelInput.SelectionChange(
                activityLevel = intent.value,
            ).reduce(currentState)
        }
    }

    private fun onChangeDietInput(
        intent: ViewIntent.OnChangeDietInputIntent
    ) {
        updateState { currentState ->
            PartialStateChange.DietInput.SelectionChange(
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
            2 -> validateWeightUseCase(state.weightInput.toDoubleOrNull()).onError {
                updateState { currentState ->
                    PartialStateChange.WeightInput.Error(it.toMessage()).reduce(currentState)
                }
            }
            3 -> validateHeightUseCase(state.heightInput.toDoubleOrNull()).onError {
                updateState { currentState ->
                    PartialStateChange.HeightInput.Error(it.toMessage()).reduce(currentState)
                }
            }
            4 -> validateGenderUseCase(state.gender).onError {
                sendEvent(ViewEvent.ShowSnackbar(it.toMessage(), SnackbarType.ERROR))
            }
            5 -> validateActivityLevelUseCase(state.activityLevel).onError {
                sendEvent(ViewEvent.ShowSnackbar(it.toMessage(), SnackbarType.ERROR))
            }
            6 -> validateDietUseCase(state.diet).onError {
                sendEvent(ViewEvent.ShowSnackbar(it.toMessage(), SnackbarType.ERROR))
            }
            else -> ValidationResult.Success
        }

        if (validationResult is ValidationResult.Success) {
            if (currentStep < 6) {
                updateState { PartialStateChange.StepChange(currentStep + 1).reduce(it) }
            } else {
                sendEvent(ViewEvent.NavigateToMainScreen)
            }
        }
    }
}