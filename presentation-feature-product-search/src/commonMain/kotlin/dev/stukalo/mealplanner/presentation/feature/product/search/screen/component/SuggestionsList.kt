package dev.stukalo.mealplanner.presentation.feature.product.search.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun SuggestionsList(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background),
        contentPadding = PaddingValues(Theme.spacing.space16)
    ) {
        items(suggestions.size) { index ->
            val suggestion = suggestions[index]
            Text(
                text = suggestion,
                style = Theme.typography.regular14,
                color = Theme.color.textPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(suggestion) }
                    .padding(vertical = Theme.spacing.space12)
            )
            if (index < suggestions.size - 1) {
                HorizontalDivider(color = Theme.color.textSecondary.copy(alpha = 0.1f))
            }
        }
    }
}

@Preview
@Composable
private fun SuggestionsListPreview() {
    Theme {
        SuggestionsList(
            suggestions = listOf("Apple", "Banana", "Cherry"),
            onSuggestionClick = {}
        )
    }
}
