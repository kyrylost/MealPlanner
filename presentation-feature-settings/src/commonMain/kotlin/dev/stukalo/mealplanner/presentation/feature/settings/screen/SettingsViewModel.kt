package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.lifecycle.viewModelScope
import dev.stukalo.mealplanner.core.common.validation.ValidationResult
import dev.stukalo.mealplanner.core.platform.LocaleManager
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.domain.usecase.health.GetHealthPermissionStatusUseCase
import dev.stukalo.mealplanner.domain.usecase.health.GetHealthServiceStatusUseCase
import dev.stukalo.mealplanner.domain.usecase.health.RequestHealthPermissionsUseCase
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
import dev.stukalo.mealplanner.presentation.feature.settings.core.mapper.HealthPermissionMapper
import dev.stukalo.mealplanner.presentation.feature.settings.core.model.HealthPermissionOption
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.EditableField
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewState
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
    private val getHealthServiceStatusUseCase: GetHealthServiceStatusUseCase,
    private val getHealthPermissionStatusUseCase: GetHealthPermissionStatusUseCase,
    private val requestHealthPermissionsUseCase: RequestHealthPermissionsUseCase,
    private val healthPermissionMapper: HealthPermissionMapper,
    private val localeManager: LocaleManager
) : BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {

    /**
     * The initial state of the Settings screen.
     */
    override val initialState = ViewState()

    init {
        getUserUseCase()
            .onEach { user ->
                updateState { PartialStateChange.UserLoaded(user).reduce(it) }
            }.launchIn(viewModelScope)

        combine(
            getLocaleUseCase(),
            getColorPaletteUseCase(),
            getThemeModeUseCase()
        ) { locale, palette, mode ->
            PartialStateChange.ConfigLoaded(
                language = locale ?: localeManager.getSystemLocale(),
                palette = palette,
                themeMode = mode
            )
        }.onEach { change ->
            updateState { change.reduce(it) }
        }.launchIn(viewModelScope)

        getHealthPermissionStatusUseCase()
            .map { healthPermissionMapper.mapListTo(it) }
            .onEach { options ->
                val status = getHealthServiceStatusUseCase()
                updateState { PartialStateChange.HealthStatusLoaded(status, options).reduce(it) }
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
            is ViewIntent.OnHealthPermissionToggle -> onHealthPermissionToggle(intent.option, intent.enabled)
            ViewIntent.OnResume -> onResume()
            ViewIntent.OnHealthPermissionsHandled -> onHealthPermissionsHandled()
            is ViewIntent.OnHealthPermissionsResult -> onHealthPermissionsResult(intent.isGranted)
            ViewIntent.OnDismissPermissionBlockedDialog -> onDismissPermissionBlockedDialog()
            ViewIntent.OnOpenHealthSettings -> onOpenHealthSettings()
            ViewIntent.OnInstallHealthConnectClick -> onInstallHealthConnectClick()
            ViewIntent.OnRequestHealthPermissions -> onRequestHealthPermissions()
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
        updateState { PartialStateChange.EditingFieldChange(field).reduce(it) }
    }

    private fun onDismissEdit() {
        updateState { PartialStateChange.EditingFieldChange(null).reduce(it) }
    }

    private fun onManualInputClick() {
        val currentField = viewState.value.editingField
        updateState { PartialStateChange.EditingFieldChange(currentField).reduce(it) }
    }

    private fun onWeightChange(weight: String) {
        updateState { PartialStateChange.TempInput.Weight(weight).reduce(it) }
        updateState { PartialStateChange.Error(null).reduce(it) }
    }

    private fun onHeightChange(height: String) {
        updateState { PartialStateChange.TempInput.Height(height).reduce(it) }
        updateState { PartialStateChange.Error(null).reduce(it) }
    }

    private fun onTargetWeightChange(targetWeight: String) {
        updateState { PartialStateChange.TempInput.TargetWeight(targetWeight).reduce(it) }
        updateState { PartialStateChange.Error(null).reduce(it) }
    }

    private fun onStepsTargetChange(steps: String) {
        updateState { PartialStateChange.TempInput.StepsTarget(steps).reduce(it) }
        updateState { PartialStateChange.Error(null).reduce(it) }
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
            val inputChange = when (field) {
                EditableField.Weight -> PartialStateChange.TempInput.Weight(value)
                EditableField.Height -> PartialStateChange.TempInput.Height(value)
                EditableField.TargetWeight -> PartialStateChange.TempInput.TargetWeight(value)
                EditableField.StepsTarget -> PartialStateChange.TempInput.StepsTarget(value)
                else -> null
            }
            inputChange?.reduce(it) ?: it
        }
        saveProfile()
    }

    /**
     * Handles the user's request to toggle a health permission.
     *
     * @param option The permission option to toggle.
     * @param enabled Whether the permission should be enabled or disabled.
     */
    private fun onHealthPermissionToggle(option: HealthPermissionOption, enabled: Boolean) {
        if (enabled) {
            viewModelScope.launch {
                val result = requestHealthPermissionsUseCase(option.group)
                val types = result.getOrDefault(defaultValue = emptySet())

                updateState {
                    it.copy(
                        shouldRequestHealthPermissions = true,
                        healthPermissionsToRequest = types
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
            val previouslyGrantedGroups =
                viewState.value.permissionOptions
                    .filter { it.isGranted }
                    .map { it.group }
                    .toSet()

            refreshPermissions()

            val currentlyGrantedGroups =
                viewState.value.permissionOptions
                    .filter { it.isGranted }
                    .map { it.group }
                    .toSet()

            val wasAnythingAdded = (currentlyGrantedGroups - previouslyGrantedGroups).isNotEmpty()

            // On iOS, isGranted is reported as true if the dialog finished.
            // We show the dialog if nothing was actually added to the granted set,
            // which implies the user either denied or the system blocked the dialog.
            val shouldShowBlocked = if (isGranted) {
                !wasAnythingAdded && currentlyGrantedGroups.size < viewState.value.permissionOptions.size
            } else {
                true
            }

            if (shouldShowBlocked) {
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

    private fun onRequestHealthPermissions() {
        viewModelScope.launch {
            val result = requestHealthPermissionsUseCase()
            onHealthPermissionsResult(isGranted = result.isSuccess && result.getOrThrow().isNotEmpty())
        }
    }

    /**
     * Refreshes the set of granted health permissions by querying the health service.
     */
    private suspend fun refreshPermissions() {
        val status = getHealthServiceStatusUseCase()
        updateState { it.copy(healthServiceStatus = status) }

        if (status == HealthServiceStatus.AVAILABLE) {
            val statuses = getHealthPermissionStatusUseCase().first()
            val options = healthPermissionMapper.mapListTo(statuses)
            updateState { PartialStateChange.HealthStatusLoaded(status, options).reduce(it) }
        }
    }

    private fun saveUser(user: UserDomainModel) {
        viewModelScope.launch {
            updateState { PartialStateChange.Saving(true).reduce(it) }
            saveUserDataUseCase(user)
            val dailyNorm = calculateDailyNormUseCase(user)
            saveDailyNormUseCase(dailyNorm)
            updateState { PartialStateChange.Saving(false).reduce(it) }
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
                updateState { PartialStateChange.Error(validationResult.exception.toMessage()).reduce(it) }
                return
            }
            weight?.let { updatedUser = updatedUser.copy(weight = it) }
        }

        if (state.tempHeightInput.isNotEmpty()) {
            val height = state.tempHeightInput.toDoubleOrNull()
            val validationResult = validateHeightUseCase(height)
            if (validationResult is ValidationResult.Error) {
                updateState { PartialStateChange.Error(validationResult.exception.toMessage()).reduce(it) }
                return
            }
            height?.let { updatedUser = updatedUser.copy(height = it) }
        }

        if (state.tempTargetWeightInput.isNotEmpty()) {
            val targetWeight = state.tempTargetWeightInput.toDoubleOrNull()
            val validationResult = validateWeightUseCase(targetWeight)
            if (validationResult is ValidationResult.Error) {
                updateState { PartialStateChange.Error(validationResult.exception.toMessage()).reduce(it) }
                return
            }
            targetWeight?.let { updatedUser = updatedUser.copy(targetWeight = it) }
        }

        if (state.tempStepsTargetInput.isNotEmpty()) {
            val steps = state.tempStepsTargetInput.toIntOrNull()
            val validationResult = validateStepsTargetUseCase(steps)
            if (validationResult is ValidationResult.Error) {
                updateState { PartialStateChange.Error(validationResult.exception.toMessage()).reduce(it) }
                return
            }
            steps?.let { updatedUser = updatedUser.copy(stepsTarget = it) }
        }

        saveUser(updatedUser)
        updateState { PartialStateChange.EditingFieldChange(null).reduce(it) }
    }
}
