package dev.stukalo.mealplanner.presentation.core.ui.component.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType

/**
 * Android implementation of [dev.stukalo.mealplanner.presentation.core.ui.component.permission.HealthPermissionGate] using Health Connect.
 */
@Composable
actual fun HealthPermissionGate(
    permissions: Set<HealthPermissionType>,
    onPermissionsGranted: (Boolean) -> Unit,
    trigger: Boolean,
    onTriggerReset: () -> Unit,
    onRequestPermissions: (suspend () -> Unit)?
) {
    val platformPermissions = permissions.map { type ->
        when (type) {
            HealthPermissionType.STEPS_READ -> HealthPermission.getReadPermission(StepsRecord::class)
            HealthPermissionType.WEIGHT_READ -> HealthPermission.getReadPermission(WeightRecord::class)
            HealthPermissionType.WEIGHT_WRITE -> HealthPermission.getWritePermission(WeightRecord::class)
            HealthPermissionType.NUTRITION_READ -> HealthPermission.getReadPermission(NutritionRecord::class)
            HealthPermissionType.NUTRITION_WRITE -> HealthPermission.getWritePermission(NutritionRecord::class)
        }
    }.toSet()

    val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()
    val launcher = rememberLauncherForActivityResult(requestPermissionActivityContract) { granted ->
        // On Android, we consider the request successful if any permission is granted.
        // The ViewModel will perform a more detailed check by refreshing the granted set.
        onPermissionsGranted(granted.isNotEmpty())
    }

    LaunchedEffect(trigger) {
        if (trigger && platformPermissions.isNotEmpty()) {
            launcher.launch(platformPermissions)
            onTriggerReset()
        } else if (trigger) {
            onTriggerReset()
        }
    }
}
