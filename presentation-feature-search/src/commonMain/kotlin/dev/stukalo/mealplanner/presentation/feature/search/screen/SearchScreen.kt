package dev.stukalo.mealplanner.presentation.feature.search.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.BottomBarHeight
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
) {
    val recipes = viewModel.recipes.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { viewModel.searchRecipes() },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Search Recipes")
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = BottomBarHeight.current)
        ) {
            items(count = recipes.itemCount) { index ->
                val recipe = recipes[index]
                if (recipe != null) {
                    RecipeItem(recipe)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun RecipeItem(recipe: RecipeDomainModel) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = recipe.product.productName ?: "Unknown Recipe",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Calories: ${recipe.product.caloriesTotal}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
