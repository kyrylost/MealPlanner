package dev.stukalo.mealplanner.presentation.feature.onboarding.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract.ViewEvent
import org.koin.compose.viewmodel.koinViewModel

/**
 * The entry point for the Onboarding feature.
 * Coordinates ViewModel, state, and navigation.
 *
 * @param onNavigateToWelcome Callback to navigate to the Welcome screen.
 */
@Composable
internal fun OnboardingScreen(onNavigateToWelcome: () -> Unit) {
    val viewModel: OnboardingViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateToWelcome -> onNavigateToWelcome()
            }
        },
        snackbarHostState = snackbarHostState
    ) { state ->
        OnboardingContent(
            state = state,
            onIntent = { viewModel.onIntent(it) }
        )
    }
}
