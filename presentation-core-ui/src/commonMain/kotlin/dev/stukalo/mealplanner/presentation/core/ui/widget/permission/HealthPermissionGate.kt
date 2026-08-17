package dev.stukalo.mealplanner.presentation.core.ui.widget.permission

import androidx.compose.runtime.Composable

@Composable
expect fun HealthPermissionGate(
    permissions: Set<String>,
    onPermissionsGranted: (Boolean) -> Unit,
    trigger: Boolean,
    onTriggerReset: () -> Unit
)
