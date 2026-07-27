package dev.stukalo.mealplanner.presentation.feature.product.search.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_product_search
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.product.search.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ProductSearchContent(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("${stringResource(Res.string.common_product_search)}: ${state.query}")
    }
}

@Preview
@Composable
private fun ProductSearchContentPreview() {
    Theme {
        ProductSearchContent(
            state = ViewState(query = "Test"),
            onIntent = {}
        )
    }
}
