package dev.stukalo.mealplanner.presentation.feature.settings.component.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Desktop implementation of [NotificationPermissionGate].
 */
@Composable
actual fun NotificationPermissionGate(
    trigger: Boolean,
    onPermissionResult: (Boolean) -> Unit,
    onTriggerReset: () -> Unit
) {
    LaunchedEffect(trigger) {
        if (trigger) {
            onPermissionResult(true)
            onTriggerReset()
        }
    }
}
