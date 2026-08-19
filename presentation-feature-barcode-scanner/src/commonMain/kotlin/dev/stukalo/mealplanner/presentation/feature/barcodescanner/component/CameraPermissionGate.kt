package dev.stukalo.mealplanner.presentation.feature.barcodescanner.component

import androidx.compose.runtime.Composable

@Composable
expect fun CameraPermissionGate(content: @Composable () -> Unit)
