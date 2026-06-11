package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import dev.stukalo.mealplanner.common.core.date.formatDate
import dev.stukalo.mealplanner.common.core.validation.ValidationResult
import dev.stukalo.mealplanner.common.core.validation.onError
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateDateUseCase
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
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.Date

internal class WelcomeViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateHeightUseCase: ValidateHeightUseCase,
    private val validateWeightUseCase: ValidateWeightUseCase,
    private val validateGenderUseCase: ValidateGenderUseCase,
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

            ViewIntent.OnShowDatePickerIntent -> {
                updateState { PartialStateChange.ShowDatePicker(true).reduce(it) }
            }

            ViewIntent.OnHideDatePickerIntent -> {
                updateState { PartialStateChange.ShowDatePicker(false).reduce(it) }
            }

            ViewIntent.OnContinueClickIntent -> {
                validateAndNavigate()
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

    private suspend fun validateAndNavigate() {
        val state = viewState.value

        coroutineScope {
            val results = listOf(
                async { validateNameUseCase(state.nameInput) },
                async { validateDateUseCase(state.dateInput) },
                async { validateHeightUseCase(state.heightInput.toDoubleOrNull()) },
                async { validateWeightUseCase(state.weightInput.toDoubleOrNull()) },
                async { validateGenderUseCase(state.gender) }
            ).awaitAll()

            val (nameResult, dateResult, heightResult, weightResult, genderResult) = results

            val hasError = results.any { it is ValidationResult.Error }

            if (hasError) {
                updateState { currentState ->
                    PartialStateChange.ValidationErrors(
                        nameErrorMessage = (nameResult as? ValidationResult.Error)?.exception?.toMessage(),
                        dateErrorMessage = (dateResult as? ValidationResult.Error)?.exception?.toMessage(),
                        heightErrorMessage = (heightResult as? ValidationResult.Error)?.exception?.toMessage(),
                        weightErrorMessage = (weightResult as? ValidationResult.Error)?.exception?.toMessage(),
                    ).reduce(currentState)
                }
                (genderResult as? ValidationResult.Error)?.let {
                    sendEvent(
                        ViewEvent.ShowSnackbar(
                            message = it.exception.toMessage(),
                            type = SnackbarType.ERROR
                        )
                    )
                }
            } else {
                sendEvent(ViewEvent.NavigateToMainScreen)
            }
        }
    }
}