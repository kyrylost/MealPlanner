package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.core.platform.LocaleManager
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.usecase.health.GetGrantedHealthPermissionsUseCase
import dev.stukalo.mealplanner.domain.usecase.health.GetHealthPermissionStringUseCase
import dev.stukalo.mealplanner.domain.usecase.health.GetHealthServiceStatusUseCase
import dev.stukalo.mealplanner.domain.usecase.health.IsHealthServiceAvailableUseCase
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
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateStepsTargetUseCase
import dev.stukalo.mealplanner.domain.usecase.validation.ValidateWeightUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.core.ui.mapper.toMessage
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.EditableField
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
    private val validateHeightUseCase: ValidateHeightUseCase,
    private val validateStepsTargetUseCase: ValidateStepsTargetUseCase,
    private val isHealthServiceAvailableUseCase: IsHealthServiceAvailableUseCase,
    private val getHealthServiceStatusUseCase: GetHealthServiceStatusUseCase,
    private val getGrantedHealthPermissionsUseCase: GetGrantedHealthPermissionsUseCase,
    private val getHealthPermissionStringUseCase: GetHealthPermissionStringUseCase,
    private val localeManager: LocaleManager
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
                updateState { it.copy(currentLanguage = locale ?: localeManager.getSystemLocale()) }
            }.launchIn(viewModelScope)

        getColorPaletteUseCase()
            .onEach { palette ->
                updateState { it.copy(currentColorPalette = palette) }
            }.launchIn(viewModelScope)

        getThemeModeUseCase()
            .onEach { mode ->
                updateState { it.copy(currentThemeMode = mode) }
            }.launchIn(viewModelScope)

        viewModelScope.launch {
            refreshPermissions()
        }
    }

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            is ViewIntent.OnColorPaletteClick -> onColorPaletteClick(intent.palette)
            is ViewIntent.OnThemeModeClick -> onThemeModeClick(intent.mode)
            is ViewIntent.OnLanguageClick -> onLanguageClick(intent.language)
            ViewIntent.OnBackClick -> onBackClick()
            is ViewIntent.OnEditFieldClick -> onEditFieldClick(intent.field)
            ViewIntent.OnDismissEdit -> onDismissEdit()
            ViewIntent.OnManualInputClick -> onManualInputClick()
            is ViewIntent.OnWeightChange -> onWeightChange(intent.weight)
            is ViewIntent.OnHeightChange -> onHeightChange(intent.height)
            is ViewIntent.OnTargetWeightChange -> onTargetWeightChange(intent.targetWeight)
            is ViewIntent.OnStepsTargetChange -> onStepsTargetChange(intent.steps)
            is ViewIntent.OnActivityLevelChange -> onActivityLevelChange(intent.activityLevel)
            is ViewIntent.OnDietTypeChange -> onDietTypeChange(intent.diet)
            ViewIntent.OnSaveProfileClick -> onSaveProfileClick()
            is ViewIntent.OnManualInputConfirm -> onManualInputConfirm(intent.value)
            is ViewIntent.OnHealthPermissionToggle -> onHealthPermissionToggle(intent.type, intent.enabled)
            ViewIntent.OnResume -> onResume()
            ViewIntent.OnHealthPermissionsHandled -> onHealthPermissionsHandled()
            is ViewIntent.OnHealthPermissionsResult -> onHealthPermissionsResult(intent.isGranted)
            ViewIntent.OnDismissPermissionBlockedDialog -> onDismissPermissionBlockedDialog()
            ViewIntent.OnOpenHealthSettings -> onOpenHealthSettings()
            ViewIntent.OnInstallHealthConnectClick -> onInstallHealthConnectClick()
        }
    }

    private fun onColorPaletteClick(palette: ColorPaletteDomainModel) {
        viewModelScope.launch {
            setColorPaletteUseCase(palette)
        }
    }

    private fun onThemeModeClick(mode: ThemeModeDomainModel) {
        viewModelScope.launch {
            setThemeModeUseCase(mode)
        }
    }

    private fun onLanguageClick(language: String) {
        viewModelScope.launch {
            setLocaleUseCase(language)
        }
    }

    private fun onBackClick() {
        viewModelScope.launch {
            sendEvent(ViewEvent.NavigateBack)
        }
    }

    private fun onEditFieldClick(field: EditableField) {
        updateState { it.copy(editingField = field) }
    }

    private fun onDismissEdit() {
        updateState { it.copy(editingField = null, isManualInputVisible = false, errorMessage = null) }
    }

    private fun onManualInputClick() {
        updateState { it.copy(isManualInputVisible = true, errorMessage = null) }
    }

    private fun onWeightChange(weight: String) {
        updateState { it.copy(tempWeightInput = weight, errorMessage = null) }
    }

    private fun onHeightChange(height: String) {
        updateState { it.copy(tempHeightInput = height, errorMessage = null) }
    }

    private fun onTargetWeightChange(targetWeight: String) {
        updateState { it.copy(tempTargetWeightInput = targetWeight, errorMessage = null) }
    }

    private fun onStepsTargetChange(steps: String) {
        updateState { it.copy(tempStepsTargetInput = steps, errorMessage = null) }
    }

    private fun onActivityLevelChange(activityLevel: ActivityLevelDomainModel) {
        val user = viewState.value.user ?: return
        saveUser(user.copy(physicalActivity = activityLevel))
    }

    private fun onDietTypeChange(diet: DietDomainModel) {
        val user = viewState.value.user ?: return
        saveUser(user.copy(diet = diet))
    }

    private fun onSaveProfileClick() {
        saveProfile()
    }

    private fun onManualInputConfirm(value: String) {
        val state = viewState.value
        val field = state.editingField ?: return

        updateState {
            when (field) {
                EditableField.Weight -> it.copy(tempWeightInput = value)
                EditableField.Height -> it.copy(tempHeightInput = value)
                EditableField.TargetWeight -> it.copy(tempTargetWeightInput = value)
                EditableField.StepsTarget -> it.copy(tempStepsTargetInput = value)
                else -> it
            }
        }
        saveProfile()
    }

    /**
     * Handles the user's request to toggle a health permission.
     *
     * @param type The type of the health permission.
     * @param enabled Whether the permission should be enabled or disabled.
     */
    private fun onHealthPermissionToggle(type: HealthPermissionType, enabled: Boolean) {
        if (enabled) {
            viewModelScope.launch {
                val permissionString = getHealthPermissionStringUseCase(type)
                updateState {
                    it.copy(
                        shouldRequestHealthPermissions = true,
                        healthPermissionsToRequest = setOf(permissionString)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                sendEvent(ViewEvent.OpenHealthSettings)
            }
        }
    }

    private fun onResume() {
        viewModelScope.launch {
            refreshPermissions()
        }
    }

    private fun onHealthPermissionsHandled() {
        updateState { it.copy(shouldRequestHealthPermissions = false) }
    }

    /**
     * Handles the result of the health permission request.
     *
     * @param isGranted Whether the permission was granted by the user.
     */
    private fun onHealthPermissionsResult(isGranted: Boolean) {
        updateState { it.copy(shouldRequestHealthPermissions = false) }
        viewModelScope.launch {
            val previouslyGranted = viewState.value.grantedPermissionTypes
            val currentlyGranted = refreshPermissions()

            // If we requested permissions but the state didn't change to granted,
            // it means the user denied or the system blocked the dialog.
            if (!isGranted && currentlyGranted.size <= previouslyGranted.size) {
                updateState { it.copy(showPermissionBlockedDialog = true) }
            }
        }
    }

    private fun onDismissPermissionBlockedDialog() {
        updateState { it.copy(showPermissionBlockedDialog = false) }
    }

    private fun onOpenHealthSettings() {
        viewModelScope.launch {
            sendEvent(ViewEvent.OpenHealthSettings)
        }
    }

    private fun onInstallHealthConnectClick() {
        viewModelScope.launch {
            sendEvent(ViewEvent.InstallHealthConnect)
        }
    }

    /**
     * Refreshes the set of granted health permissions by querying the health service.
     *
     * @return The set of currently granted [HealthPermissionType]s.
     */
    private suspend fun refreshPermissions(): Set<HealthPermissionType> {
        val status = getHealthServiceStatusUseCase()
        updateState { it.copy(healthServiceStatus = status) }

        if (isHealthServiceAvailableUseCase()) {
            val grantedStrings = getGrantedHealthPermissionsUseCase()
            val grantedTypes = HealthPermissionType.entries.filter { type ->
                val systemString = getHealthPermissionStringUseCase(type)
                grantedStrings.contains(systemString)
            }.toSet()

            updateState { it.copy(grantedPermissionTypes = grantedTypes) }
            return grantedTypes
        }
        return emptySet()
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

    /**
     * Validates temporary inputs and saves the updated user profile.
     */
    private fun saveProfile() {
        val state = viewState.value
        val user = state.user ?: return

        var updatedUser = user

        if (state.tempWeightInput.isNotEmpty()) {
            val weight = state.tempWeightInput.toDoubleOrNull()
            val validationResult = validateWeightUseCase(weight)
            if (validationResult is ValidationResult.Error) {
                updateState { it.copy(errorMessage = validationResult.exception.toMessage()) }
                return
            }
            weight?.let { updatedUser = updatedUser.copy(weight = it) }
        }

        if (state.tempHeightInput.isNotEmpty()) {
            val height = state.tempHeightInput.toDoubleOrNull()
            val validationResult = validateHeightUseCase(height)
            if (validationResult is ValidationResult.Error) {
                updateState { it.copy(errorMessage = validationResult.exception.toMessage()) }
                return
            }
            height?.let { updatedUser = updatedUser.copy(height = it) }
        }

        if (state.tempTargetWeightInput.isNotEmpty()) {
            val targetWeight = state.tempTargetWeightInput.toDoubleOrNull()
            val validationResult = validateWeightUseCase(targetWeight)
            if (validationResult is ValidationResult.Error) {
                updateState { it.copy(errorMessage = validationResult.exception.toMessage()) }
                return
            }
            targetWeight?.let { updatedUser = updatedUser.copy(targetWeight = it) }
        }

        if (state.tempStepsTargetInput.isNotEmpty()) {
            val steps = state.tempStepsTargetInput.toIntOrNull()
            val validationResult = validateStepsTargetUseCase(steps)
            if (validationResult is ValidationResult.Error) {
                updateState { it.copy(errorMessage = validationResult.exception.toMessage()) }
                return
            }
            steps?.let { updatedUser = updatedUser.copy(stepsTarget = it) }
        }

        saveUser(updatedUser)
        updateState {
            it.copy(
                editingField = null,
                isManualInputVisible = false,
                tempWeightInput = "",
                tempHeightInput = "",
                tempTargetWeightInput = "",
                tempStepsTargetInput = "",
                errorMessage = null
            )
        }
    }
}
