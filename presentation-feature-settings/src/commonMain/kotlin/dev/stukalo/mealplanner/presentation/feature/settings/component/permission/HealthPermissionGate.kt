package dev.stukalo.mealplanner.presentation.feature.settings.component.permission

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType

/**
 * A platform-specific gate for requesting health permissions.
 *
 * @param permissions The set of domain-level permission types to request.
 * @param onPermissionsGranted Callback triggered when the permission request is complete.
 *                             Receives true if the request was considered successful.
 * @param trigger When true, the platform permission dialog will be shown.
 * @param onTriggerReset Callback to reset the trigger state.
 * @param onRequestPermissions Optional callback for manual permission request triggering.
 */
@Composable
expect fun HealthPermissionGate(
    permissions: Set<HealthPermissionType>,
    onPermissionsGranted: (Boolean) -> Unit,
    trigger: Boolean,
    onTriggerReset: () -> Unit,
    onRequestPermissions: (suspend () -> Unit)? = null
)
