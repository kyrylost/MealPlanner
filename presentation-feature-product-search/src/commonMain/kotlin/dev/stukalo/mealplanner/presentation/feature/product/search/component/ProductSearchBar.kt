package dev.stukalo.mealplanner.presentation.feature.product.search.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_product_search
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.input.RoundedPlaceholderTextField
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconSearch
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProductSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onAction: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = Theme.spacing.space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedPlaceholderTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = stringResource(Res.string.common_product_search),
            imeAction = ImeAction.Search,
            onAction = onAction,
            leadingIcon = {
                Icon(
                    imageVector = IconSearch,
                    contentDescription = null,
                    tint = Theme.color.text.secondary.copy(alpha = 0.5f),
                    modifier = Modifier.size(Theme.spacing.space20)
                )
            },
            backgroundColor = Theme.color.background.secondary.copy(alpha = 0.5f),
            cornerRadiusDp = Theme.radius.radius16,
            contentPaddingDp = Theme.spacing.space12,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
private fun ProductSearchBarPreview() {
    Theme {
        ProductSearchBar(
            query = "Apple",
            onQueryChange = {}
        )
    }
}
