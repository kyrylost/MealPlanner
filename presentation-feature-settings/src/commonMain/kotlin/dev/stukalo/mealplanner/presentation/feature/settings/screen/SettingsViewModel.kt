package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.usecase.setting.GetColorPaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.GetThemeModeUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetColorPaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemeModeUseCase
import dev.stukalo.mealplanner.domain.usecase.user.CalculateDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import dev.stukalo.mealplanner.domain.usecase.user.SaveDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.user.SaveUserDataUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateHeightUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateWeightUseCase
import dev.stukalo.mealplanner.presentation.core.platform.getSystemLocale
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.core.ui.mapper.toMessage
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ViewModel for the Settings screen.
 * Handles theme changes, locale updates, and user profile modifications.
 */
class SettingsViewModel(
    private val setColorPaletteUseCase: SetColorPaletteUseCase,
    getColorPaletteUseCase: GetColorPaletteUseCase,
    private val setThemeModeUseCase: SetThemeModeUseCase,
    getThemeModeUseCase: GetThemeModeUseCase,
    private val setLocaleUseCase: SetLocaleUseCase,
    getLocaleUseCase: GetLocaleUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    getUserUseCase: GetUserUseCase,
    private val calculateDailyNormUseCase: CalculateDailyNormUseCase,
    private val saveDailyNormUseCase: SaveDailyNormUseCase,
    private val validateWeightUseCase: ValidateWeightUseCase,
    private val validateHeightUseCase: ValidateHeightUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

    /**
     * The initial state of the Settings screen.
     */
    override val initialState = ViewState()

    init {
        getUserUseCase()
            .onEach { user ->
                updateState { it.copy(user = user) }
            }.launchIn(viewModelScope)

        getLocaleUseCase()
            .onEach { locale ->
                updateState { it.copy(currentLanguage = locale ?: getSystemLocale()) }
            }.launchIn(viewModelScope)

        getColorPaletteUseCase()
            .onEach { palette ->
                updateState { it.copy(currentColorPalette = palette) }
            }.launchIn(viewModelScope)

        getThemeModeUseCase()
            .onEach { mode ->
                updateState { it.copy(currentThemeMode = mode) }
            }.launchIn(viewModelScope)
    }

    /**
     * Processes user intents and updates the state or sends single events.
     *
     * @param intent The [ViewIntent] to process.
     */
    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnColorPaletteClick -> {
                viewModelScope.launch {
                    setColorPaletteUseCase(intent.palette)
                }
            }

            is ViewIntent.OnThemeModeClick -> {
                viewModelScope.launch {
                    setThemeModeUseCase(intent.mode)
                }
            }

            is ViewIntent.OnLanguageClick -> {
                viewModelScope.launch {
                    setLocaleUseCase(intent.language)
                }
            }

            ViewIntent.OnBackClick -> {
                sendEvent(ViewEvent.NavigateBack)
            }

            is ViewIntent.OnEditFieldClick -> {
                updateState { it.copy(editingField = intent.field) }
            }

            ViewIntent.OnDismissEdit -> {
                updateState { it.copy(editingField = null, isManualInputVisible = false, errorMessage = null) }
            }

            ViewIntent.OnManualInputClick -> {
                updateState { it.copy(isManualInputVisible = true, errorMessage = null) }
            }

            is ViewIntent.OnWeightChange -> {
                updateState { it.copy(tempWeightInput = intent.weight, errorMessage = null) }
            }

            is ViewIntent.OnHeightChange -> {
                updateState { it.copy(tempHeightInput = intent.height, errorMessage = null) }
            }

            is ViewIntent.OnTargetWeightChange -> {
                updateState { it.copy(tempTargetWeightInput = intent.targetWeight, errorMessage = null) }
            }

            is ViewIntent.OnActivityLevelChange -> {
                val user = viewState.value.user ?: return
                saveUser(user.copy(physicalActivity = intent.activityLevel))
            }

            is ViewIntent.OnDietTypeChange -> {
                val user = viewState.value.user ?: return
                saveUser(user.copy(diet = intent.diet))
            }

            ViewIntent.OnSaveProfileClick -> {
                val state = viewState.value
                val user = state.user ?: return

                var updatedUser = user

                if (state.tempWeightInput.isNotEmpty()) {
                    val weight = state.tempWeightInput.toDoubleOrNull()
                    val validationResult = validateWeightUseCase(weight)
                    if (validationResult is dev.stukalo.mealplanner.common.core.validation.ValidationResult.Error) {
                        updateState { it.copy(errorMessage = validationResult.exception.toMessage()) }
                        return
                    }
                    updatedUser = updatedUser.copy(weight = weight!!)
                }

                if (state.tempHeightInput.isNotEmpty()) {
                    val height = state.tempHeightInput.toDoubleOrNull()
                    val validationResult = validateHeightUseCase(height)
                    if (validationResult is dev.stukalo.mealplanner.common.core.validation.ValidationResult.Error) {
                        updateState { it.copy(errorMessage = validationResult.exception.toMessage()) }
                        return
                    }
                    updatedUser = updatedUser.copy(height = height!!)
                }

                if (state.tempTargetWeightInput.isNotEmpty()) {
                    val targetWeight = state.tempTargetWeightInput.toDoubleOrNull()
                    val validationResult = validateWeightUseCase(targetWeight)
                    if (validationResult is dev.stukalo.mealplanner.common.core.validation.ValidationResult.Error) {
                        updateState { it.copy(errorMessage = validationResult.exception.toMessage()) }
                        return
                    }
                    updatedUser = updatedUser.copy(targetWeight = targetWeight!!)
                }

                saveUser(updatedUser)
                updateState {
                    it.copy(
                        editingField = null,
                        isManualInputVisible = false,
                        tempWeightInput = "",
                        tempHeightInput = "",
                        tempTargetWeightInput = "",
                        errorMessage = null
                    )
                }
            }
        }
    }

    private fun saveUser(user: UserDomainModel) {
        viewModelScope.launch {
            updateState { it.copy(isSaving = true) }
            saveUserDataUseCase(user)
            val dailyNorm = calculateDailyNormUseCase(user)
            saveDailyNormUseCase(dailyNorm)
            updateState { it.copy(isSaving = false) }
        }
    }
}
