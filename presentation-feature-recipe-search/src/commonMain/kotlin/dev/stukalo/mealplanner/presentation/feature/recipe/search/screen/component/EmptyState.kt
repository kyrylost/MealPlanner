package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_clear_filters
import dev.stukalo.mealplanner.core.localization.common_no_results
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyState(
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Theme.spacing.space64),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.common_no_results),
            style = Theme.typography.bold16,
            color = Theme.color.textSecondary
        )
        Spacer(modifier = Modifier.height(Theme.spacing.space16))
        Button(onClick = onClearFilters) {
            Text(stringResource(Res.string.common_clear_filters))
        }
    }
}

@Preview
@Composable
private fun EmptyStatePreview() {
    Theme {
        EmptyState(onClearFilters = {})
    }
}
