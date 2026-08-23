package dev.stukalo.mealplanner.presentation.core.ui.component.permission

import androidx.compose.runtime.Composable

/**
 * Platform-agnostic component for handling notification permission requests.
 *
 * @param trigger When true, initiates the platform-specific permission request flow.
 * @param onPermissionResult Callback triggered when the permission request finishes.
 * @param onTriggerReset Callback to reset the trigger state in the ViewModel.
 */
@Composable
expect fun NotificationPermissionGate(
    trigger: Boolean,
    onPermissionResult: (Boolean) -> Unit,
    onTriggerReset: () -> Unit
)
