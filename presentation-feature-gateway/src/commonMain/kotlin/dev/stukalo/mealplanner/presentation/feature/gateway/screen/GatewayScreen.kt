package dev.stukalo.mealplanner.presentation.feature.gateway.screen

import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.gateway.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GatewayScreen(onNavigateToMain: () -> Unit, onNavigateToWelcome: () -> Unit) {
    val viewModel: GatewayViewModel = koinViewModel()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateToMain -> onNavigateToMain()
                ViewEvent.NavigateToWelcome -> onNavigateToWelcome()
            }
        },
        content = { state ->
            GatewayContent(state = state)
        }
    )
}
