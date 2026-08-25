package dev.stukalo.mealplanner.presentation.core.ui.base.mvi

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSideEffect
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.AppSnackbarVisuals
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.SnackbarModel
import kotlinx.coroutines.launch

/**
 * MviScreen is a composable function that represents a screen with MVI architecture.
 *
 * IMPORTANT for AI: All feature screens MUST use this pattern.
 * Refer to ARCHITECTURE.md for the Screen/Content separation rule.
 *
 * @param viewModel is a [BaseMviViewModel] that contains the business logic of the screen.
 * @param onSingleEvent is a lambda function that will be called when a feature-specific event is emitted.
 * @param snackbarHostState is an optional [SnackbarHostState] to automatically handle [MviSingleEvent.ShowSnackbar].
 * @param content is a lambda function that collects the intents and the state of the screen.
 */
@Composable
fun <I : MviIntent, S : MviViewState, E : MviSingleEvent> MviScreen(
    viewModel: BaseMviViewModel<I, S, E>,
    onSingleEvent: suspend (E) -> Unit,
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable (state: S) -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { effect ->
            when (effect) {
                is MviSideEffect.System -> {
                    val event = effect.event
                    if (event is MviSingleEvent.ShowSnackbar && snackbarHostState != null) {
                        launch {
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
                }

                is MviSideEffect.Feature -> {
                    onSingleEvent(effect.event)
                }
            }
        }
    }

    content(viewState)
}
