package dev.stukalo.mealplanner.presentation.core.ui.widget.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun HealthPermissionGate(
    permissions: Set<String>,
    onPermissionsGranted: (Boolean) -> Unit,
    trigger: Boolean,
    onTriggerReset: () -> Unit
) {
    LaunchedEffect(trigger) {
        if (trigger) {
            onPermissionsGranted(false)
            onTriggerReset()
        }
    }
}
