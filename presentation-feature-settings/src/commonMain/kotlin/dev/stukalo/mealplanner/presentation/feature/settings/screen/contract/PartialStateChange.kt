package dev.stukalo.mealplanner.presentation.feature.settings.screen.contract

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import dev.stukalo.mealplanner.presentation.feature.settings.core.model.HealthPermissionOption
import org.jetbrains.compose.resources.StringResource

internal sealed interface PartialStateChange {
    fun reduce(oldState: ViewState): ViewState

    data class UserLoaded(val user: UserDomainModel?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(user = user)
    }

    data class ConfigLoaded(
        val language: String,
        val palette: ColorPaletteDomainModel,
        val themeMode: ThemeModeDomainModel
    ) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            currentLanguage = language,
            currentColorPalette = palette,
            currentThemeMode = themeMode
        )
    }

    data class HealthStatusLoaded(val status: HealthServiceStatus, val options: List<HealthPermissionOption>) :
        PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            healthServiceStatus = status,
            permissionOptions = options
        )
    }

    data class EditingFieldChange(val field: EditableField?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            editingField = field,
            isManualInputVisible = field != null,
            tempWeightInput = oldState.user?.weight?.toString().orEmpty(),
            tempHeightInput = oldState.user?.height?.toString().orEmpty(),
            tempTargetWeightInput = oldState.user?.targetWeight?.toString().orEmpty(),
            tempStepsTargetInput = oldState.user?.stepsTarget?.toString().orEmpty(),
            errorMessage = null
        )
    }

    sealed interface TempInput : PartialStateChange {
        data class Weight(val value: String) : TempInput
        data class Height(val value: String) : TempInput
        data class TargetWeight(val value: String) : TempInput
        data class StepsTarget(val value: String) : TempInput

        override fun reduce(oldState: ViewState): ViewState = when (this) {
            is Weight -> oldState.copy(tempWeightInput = value)
            is Height -> oldState.copy(tempHeightInput = value)
            is TargetWeight -> oldState.copy(tempTargetWeightInput = value)
            is StepsTarget -> oldState.copy(tempStepsTargetInput = value)
        }
    }

    data class Saving(val isSaving: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isSaving = isSaving)
    }

    data class Error(val message: StringResource?) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(errorMessage = message)
    }

    data class MealRemindersStatusChange(val enabled: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(isMealRemindersEnabled = enabled)
    }

    data class HealthPermissionsTriggered(val types: Set<HealthPermissionType>) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(
            shouldRequestHealthPermissions = true,
            healthPermissionsToRequest = types
        )
    }

    object HealthPermissionsHandled : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(shouldRequestHealthPermissions = false)
    }

    object NotificationPermissionTriggered : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(shouldRequestNotificationPermission = true)
    }

    object NotificationPermissionHandled : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(shouldRequestNotificationPermission = false)
    }

    data class PermissionBlockedDialogVisibility(val isVisible: Boolean) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(showPermissionBlockedDialog = isVisible)
    }

    data class HealthServiceStatusChange(val status: HealthServiceStatus) : PartialStateChange {
        override fun reduce(oldState: ViewState): ViewState = oldState.copy(healthServiceStatus = status)
    }
}
