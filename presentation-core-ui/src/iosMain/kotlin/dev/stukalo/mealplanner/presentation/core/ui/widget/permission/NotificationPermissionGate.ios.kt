package dev.stukalo.mealplanner.presentation.core.ui.component.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter

/**
 * iOS implementation of [NotificationPermissionGate].
 */
@Composable
actual fun NotificationPermissionGate(
    trigger: Boolean,
    onPermissionResult: (Boolean) -> Unit,
    onTriggerReset: () -> Unit
) {
    LaunchedEffect(trigger) {
        if (trigger) {
            val center = UNUserNotificationCenter.currentNotificationCenter()
            center.requestAuthorizationWithOptions(
                UNAuthorizationOptionAlert or UNAuthorizationOptionSound
            ) { granted, error ->
                onPermissionResult(granted)
            }
            onTriggerReset()
        }
    }
}
