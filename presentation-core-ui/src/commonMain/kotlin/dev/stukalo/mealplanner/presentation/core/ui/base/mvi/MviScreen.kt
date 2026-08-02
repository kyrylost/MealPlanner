package dev.stukalo.mealplanner.presentation.core.ui.base.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState

/**
 * MviScreen is a composable function that represents a screen with MVI architecture.
 *
 * IMPORTANT for AI: All feature screens MUST use this pattern.
 * Refer to ARCHITECTURE.md for the Screen/Content separation rule.
 *
 * @param viewModel is a [BaseMviViewModel] that contains the business logic of the screen.
 * @param onSingleEvent is a lambda function that will be called when a single event is emitted.
 * @param content is a lambda function that collects the intents and the state of the screen.
 */
@Composable
fun <I : MviIntent, S : MviViewState, E : MviSingleEvent> MviScreen(
    viewModel: BaseMviViewModel<I, S, E>,
    onSingleEvent: suspend (E) -> Unit,
    content: @Composable (state: S) -> Unit
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            onSingleEvent(event)
        }
    }

    content(viewState)
}
