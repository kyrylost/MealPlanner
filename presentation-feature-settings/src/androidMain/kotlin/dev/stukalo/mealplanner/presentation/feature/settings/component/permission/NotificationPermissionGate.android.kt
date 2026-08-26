package dev.stukalo.mealplanner.presentation.feature.settings.component.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Android implementation of [NotificationPermissionGate].
 * Handles POST_NOTIFICATIONS permission on Android 13+.
 */
@Composable
actual fun NotificationPermissionGate(
    trigger: Boolean,
    onPermissionResult: (Boolean) -> Unit,
    onTriggerReset: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    LaunchedEffect(trigger) {
        if (trigger) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                onPermissionResult(true)
            }
            onTriggerReset()
        }
    }
}
