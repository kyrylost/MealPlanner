package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.data.network.fooddatacentral.source.FoodDataCentralNetSource
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onNavigateToBarcodeScanner: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToRecipeDetails: (String) -> Unit,
) {
    val fdcNetSource: FoodDataCentralNetSource = koinInject()
    val scope = rememberCoroutineScope()
    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "HomeScreen",
            modifier = Modifier
                .statusBarsPadding()
        )

        Button(onClick = onNavigateToBarcodeScanner) {
            Text("Go to Barcode Scanner")
        }

        Button(onClick = onNavigateToSearch) {
            Text("Search Recipes")
        }

        Button(onClick = { onNavigateToRecipeDetails("test_recipe_123") }) {
            Text("Go to Recipe Details")
        }

        Button(onClick = {
            scope.launch {
                try {
                    val searchResult = fdcNetSource.searchProduct("cheddar cheese", 10, 1)
                    resultText = "FDC search result: $searchResult"
                } catch (e: Exception) {
                    resultText = "FDC Error: ${e.message}"
                    e.printStackTrace()
                }
            }
        }) {
            Text("Test Fdc searchFood")
        }

        Text(text = resultText)
    }
}
