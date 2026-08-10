package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.usecase.setting.GetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetLocaleUseCase
import dev.stukalo.mealplanner.domain.usecase.setting.SetThemePaletteUseCase
import dev.stukalo.mealplanner.domain.usecase.user.CalculateDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.user.GetUserUseCase
import dev.stukalo.mealplanner.domain.usecase.user.SaveDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.user.SaveUserDataUseCase
import dev.stukalo.mealplanner.presentation.core.platform.getSystemLocale
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
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
    private val setThemePaletteUseCase: SetThemePaletteUseCase,
    getLocaleUseCase: GetLocaleUseCase,
    private val setLocaleUseCase: SetLocaleUseCase,
    getUserUseCase: GetUserUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val calculateDailyNormUseCase: CalculateDailyNormUseCase,
    private val saveDailyNormUseCase: SaveDailyNormUseCase
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
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
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnThemeClick -> {
                viewModelScope.launch {
                    setThemePaletteUseCase(intent.palette)
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
                updateState { it.copy(editingField = null) }
            }

            is ViewIntent.OnWeightChange -> {
                updateState { it.copy(tempWeightInput = intent.weight) }
            }

            is ViewIntent.OnHeightChange -> {
                updateState { it.copy(tempHeightInput = intent.height) }
            }

            is ViewIntent.OnTargetWeightChange -> {
                updateState { it.copy(tempTargetWeightInput = intent.targetWeight) }
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
                    state.tempWeightInput.toDoubleOrNull()?.let {
                        updatedUser = updatedUser.copy(weight = it)
                    }
                }

                if (state.tempHeightInput.isNotEmpty()) {
                    state.tempHeightInput.toDoubleOrNull()?.let {
                        updatedUser = updatedUser.copy(height = it)
                    }
                }

                if (state.tempTargetWeightInput.isNotEmpty()) {
                    state.tempTargetWeightInput.toDoubleOrNull()?.let {
                        updatedUser = updatedUser.copy(targetWeight = it)
                    }
                }

                saveUser(updatedUser)
                updateState {
                    it.copy(
                        editingField = null,
                        tempWeightInput = "",
                        tempHeightInput = "",
                        tempTargetWeightInput = ""
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
