package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.barcode_scanner_enter_barcode
import dev.stukalo.mealplanner.core.localization.barcode_scanner_error_result
import dev.stukalo.mealplanner.core.localization.barcode_scanner_product_result
import dev.stukalo.mealplanner.core.localization.barcode_scanner_title
import dev.stukalo.mealplanner.data.network.openfoodfacts.source.OpenFoodFactsNetSource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun BarcodeScannerScreen() {
    val openFoodFactsNetSource: OpenFoodFactsNetSource = koinInject()
    val scope = rememberCoroutineScope()
    var barcode by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(stringResource(Res.string.barcode_scanner_title))

        CameraPermissionGate {
            BarcodeScannerView(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 8.dp),
                onBarcodeScanned = {
                    barcode = it
                }
            )
        }

        OutlinedTextField(
            value = barcode,
            onValueChange = { barcode = it },
            label = { Text(stringResource(Res.string.barcode_scanner_enter_barcode)) },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                scope.launch {
                    try {
                        println("Scanning barcode: $barcode")
                        val response = openFoodFactsNetSource.getProductByBarcode(barcode)
                        println("Scanned data: $response")
                        resultText = getString(Res.string.barcode_scanner_product_result, response.toString())
                    } catch (e: Exception) {
                        resultText = getString(Res.string.barcode_scanner_error_result, e.message ?: "")
                        e.printStackTrace()
                    }
                }
            }
        ) {
            Text("Test getProductByBarcode")
        }

        Text(text = resultText, modifier = Modifier.padding(top = 16.dp))
    }
}
