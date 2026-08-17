package dev.stukalo.mealplanner.presentation.feature.settings.screen.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.row.SettingsToggleOption
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewState

/**
 * A toggle switch for health permissions in the settings.
 *
 * @param title The title of the setting.
 * @param description The description of the setting.
 * @param type The type of health permission.
 * @param state The current view state.
 * @param onIntent Callback to handle user intents.
 * @param onShowRevokeDialog Callback to show the revoke permission dialog.
 */
@Composable
internal fun HealthSyncToggle(
    title: String,
    description: String,
    type: HealthPermissionType,
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
    onShowRevokeDialog: () -> Unit
) {
    val isGranted = state.grantedPermissionTypes.contains(type)

    SettingsToggleOption(
        title = title,
        description = description,
        checked = isGranted,
        onCheckedChange = { enabled ->
            if (isGranted && !enabled) {
                onShowRevokeDialog()
            } else {
                onIntent(ViewIntent.OnHealthPermissionToggle(type, enabled))
            }
        }
    )
}

@Preview
@Composable
private fun HealthSyncTogglePreview() {
    Theme {
        HealthSyncToggle(
            title = "Steps",
            description = "Read steps data",
            type = HealthPermissionType.STEPS_READ,
            state = ViewState(),
            onIntent = {},
            onShowRevokeDialog = {}
        )
    }
}
