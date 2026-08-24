package dev.stukalo.mealplanner.presentation.feature.barcodescanner.component

import androidx.compose.runtime.Composable

@Composable
internal expect fun CameraPermissionGate(content: @Composable () -> Unit)
