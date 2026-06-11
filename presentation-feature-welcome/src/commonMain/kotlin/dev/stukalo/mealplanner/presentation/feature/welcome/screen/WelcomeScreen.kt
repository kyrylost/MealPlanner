package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.AppSnackbarHost
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model.AppSnackbarVisuals
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model.SnackbarModel
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model.SnackbarType
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewEvent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreen(
    onNavigateToMain: () -> Unit,
) {
    val viewModel: WelcomeViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                is ViewEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            AppSnackbarVisuals(
                                model = SnackbarModel(
                                    message = event.message,
                                    type = event.type
                                )
                            )
                        )
                    }
                }

                is ViewEvent.NavigateToMainScreen -> onNavigateToMain()
            }
        },
        content = { state ->
            Box(modifier = Modifier.fillMaxSize()) {
                WelcomeContent(
                    state = state,
                    snackbarHostState = snackbarHostState,
                    onIntent = viewModel::onIntent
                )
            }
        },
    )
}
