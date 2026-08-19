package dev.stukalo.mealplanner.presentation.feature.settings.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.settings_health_sync_steps_read
import dev.stukalo.mealplanner.core.localization.settings_health_sync_steps_read_desc
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.presentation.core.ui.component.row.SettingsToggleOption
import dev.stukalo.mealplanner.presentation.feature.settings.core.model.HealthPermissionOption
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import org.jetbrains.compose.resources.stringResource

/**
 * A toggle switch for health permissions in the settings.
 *
 * @param option The health permission option.
 * @param onIntent Callback to handle user intents.
 * @param onShowRevokeDialog Callback to show the revoke permission dialog.
 */
@Composable
internal fun HealthSyncToggle(
    option: HealthPermissionOption,
    onIntent: (ViewIntent) -> Unit,
    onShowRevokeDialog: () -> Unit
) {
    SettingsToggleOption(
        title = stringResource(option.title),
        description = stringResource(option.description),
        checked = option.isGranted,
        onCheckedChange = { enabled ->
            if (option.isGranted && !enabled) {
                onShowRevokeDialog()
            } else {
                onIntent(ViewIntent.OnHealthPermissionToggle(option, enabled))
            }
        }
    )
}

@Preview
@Composable
private fun HealthSyncTogglePreview() {
    HealthSyncToggle(
        option = HealthPermissionOption(
            group = HealthPermissionGroup.STEPS,
            title = Res.string.settings_health_sync_steps_read,
            description = Res.string.settings_health_sync_steps_read_desc,
            isGranted = true
        ),
        onIntent = {},
        onShowRevokeDialog = {}
    )
}
