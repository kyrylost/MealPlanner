package dev.stukalo.mealplanner.presentation.feature.barcodescanner.component

import androidx.compose.runtime.Composable

@Composable
actual fun CameraPermissionGate(content: @Composable () -> Unit) {
    // For now, just show the content.
    // In a real app, you would check for camera permissions here using platform APIs.
    content()
}
