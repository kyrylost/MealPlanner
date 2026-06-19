package dev.stukalo.mealplanner.presentation.feature.recipedetails.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    onBackClick: () -> Unit,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Recipe Details for ID: $recipeId")
        }
    }
}

@Preview
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetailsScreen(
        recipeId = "123",
        onBackClick = {}
    )
}
