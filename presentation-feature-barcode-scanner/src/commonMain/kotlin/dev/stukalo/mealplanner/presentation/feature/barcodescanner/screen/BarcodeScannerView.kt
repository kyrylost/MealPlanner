package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun BarcodeScannerView(modifier: Modifier = Modifier, onBarcodeScanned: (String) -> Unit)
