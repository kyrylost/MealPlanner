package dev.stukalo.mealplanner.presentation.core.ui.component.permission

import androidx.compose.runtime.Composable

import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType

/**
 * A platform-specific gate for requesting health permissions.
 *
 * @param permissions The set of domain-level permission types to request.
 * @param onPermissionsGranted Callback triggered when the permission request is complete.
 *                             Receives true if all requested permissions were granted.
 * @param trigger When true, the platform permission dialog will be shown.
 * @param onTriggerReset Callback to reset the trigger state in the ViewModel.
 * @param onRequestPermissions Optional callback for manual permission request triggering (used on iOS).
 */
@Composable
expect fun HealthPermissionGate(
    permissions: Set<HealthPermissionType>,
    onPermissionsGranted: (Boolean) -> Unit,
    trigger: Boolean,
    onTriggerReset: () -> Unit,
    onRequestPermissions: (suspend () -> Unit)? = null
)
