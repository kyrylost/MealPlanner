package dev.stukalo.mealplanner.presentation.feature.gateway.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.progress.AppLoader
import dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract.ViewState

@Composable
internal fun GatewayContent(state: ViewState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            AppLoader()
        }
    }
}

@Preview
@Composable
private fun GatewayContentPreview() {
    Theme {
        Surface(color = Theme.color.background) {
            GatewayContent(
                state = ViewState(isLoading = true)
            )
        }
    }
}
