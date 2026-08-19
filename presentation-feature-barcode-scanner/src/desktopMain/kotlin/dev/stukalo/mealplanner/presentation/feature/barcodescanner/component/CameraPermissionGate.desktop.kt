package dev.stukalo.mealplanner.presentation.feature.barcodescanner.component

import androidx.compose.runtime.Composable

@Composable
actual fun CameraPermissionGate(content: @Composable () -> Unit) {
    content()
}
