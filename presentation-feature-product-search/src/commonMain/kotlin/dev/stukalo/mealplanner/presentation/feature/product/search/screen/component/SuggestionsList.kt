package dev.stukalo.mealplanner.presentation.feature.product.search.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme

/**
 * A list of search suggestions displayed as an overlay.
 *
 * @param suggestions The list of suggestion strings to display.
 * @param onSuggestionClick Callback invoked when a suggestion is clicked.
 * @param modifier The modifier to be applied to the list.
 */
@Composable
fun SuggestionsList(suggestions: List<String>, onSuggestionClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = SUGGESTIONS_LIST_MAX_HEIGHT)
            .padding(horizontal = Theme.spacing.space16),
        shape = Theme.shape.normalRoundedCornerShape,
        color = Theme.color.background.primary,
        shadowElevation = Theme.elevation.normal
    ) {
        LazyColumn(
            contentPadding = PaddingValues(Theme.spacing.space16)
        ) {
            items(suggestions.size) { index ->
                val suggestion = suggestions[index]
                Text(
                    text = suggestion,
                    style = Theme.typography.regular14,
                    color = Theme.color.text.primary,
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onSuggestionClick(suggestion) }
                        .padding(vertical = Theme.spacing.space12)
                )
                if (index < suggestions.size - 1) {
                    HorizontalDivider(color = Theme.color.text.secondary.copy(alpha = 0.1f))
                }
            }
        }
    }
}

private val SUGGESTIONS_LIST_MAX_HEIGHT = 254.dp

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
