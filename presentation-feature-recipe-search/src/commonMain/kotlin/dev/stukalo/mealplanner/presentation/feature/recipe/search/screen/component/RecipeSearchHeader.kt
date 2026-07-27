package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_filters
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipeSearchHeader(
    onFiltersClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(vertical = Theme.spacing.space16)
    ) {
        Button(
            onClick = onFiltersClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(Res.string.common_filters))
        }
    }
}

@Preview
@Composable
private fun RecipeSearchHeaderPreview() {
    Theme {
        RecipeSearchHeader(onFiltersClick = {})
    }
}
