package dev.stukalo.mealplanner.presentation.core.ui.widget.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.health.connect.client.PermissionController

@Composable
actual fun HealthPermissionGate(
    permissions: Set<String>,
    onPermissionsGranted: (Boolean) -> Unit,
    trigger: Boolean,
    onTriggerReset: () -> Unit
) {
    val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()
    val launcher = rememberLauncherForActivityResult(requestPermissionActivityContract) { granted ->
        onPermissionsGranted(granted.containsAll(permissions))
    }

    LaunchedEffect(trigger) {
        if (trigger) {
            launcher.launch(permissions)
            onTriggerReset()
        }
    }
}
