package dev.stukalo.mealplanner.presentation.core.ui.widget.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType

/**
 * iOS implementation of [HealthPermissionGate] using HealthKit.
 */
@Composable
actual fun HealthPermissionGate(
    permissions: Set<HealthPermissionType>,
    onPermissionsGranted: (Boolean) -> Unit,
    trigger: Boolean,
    onTriggerReset: () -> Unit,
    onRequestPermissions: (suspend () -> Unit)?
) {
    LaunchedEffect(trigger) {
        if (trigger) {
            onRequestPermissions?.invoke()
            // We report true here because the dialog finished.
            // The ViewModel will then call refreshPermissions to get the actual state.
            onPermissionsGranted(true)
            onTriggerReset()
        }
    }
}
