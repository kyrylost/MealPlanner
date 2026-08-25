package dev.stukalo.mealplanner.presentation.core.ui.base.mvi

import dev.stukalo.mealplanner.presentation.core.ui.base.BaseViewModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviPartialStateChange
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSideEffect
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.SnackbarType
import dev.stukalo.mealplanner.presentation.core.ui.mapper.toMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * Base class for all MVI ViewModels in the project.
 *
 * This class orchestrates the MVI (Model-View-Intent) flow:
 * 1. **Intent**: User actions or system triggers delivered via [onIntent].
 * 2. **State**: The UI state represented by [viewState].
 * 3. **PartialStateChange**: Reducers used to update the state via [reduce].
 * 4. **SingleEvent**: One-time side effects (navigation, snackbars) sent via [sendEvent].
 *
 * IMPORTANT for AI: All feature ViewModels MUST inherit from this class.
 * Consult ARCHITECTURE.md for details on the architecture pattern.
 *
 * @param I The type of [MviIntent] handled by this ViewModel.
 * @param S The type of [MviViewState] representing the UI state.
 * @param E The type of [MviSingleEvent] emitted for side effects.
 */
abstract class BaseMviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> : BaseViewModel() {
    /**
     * The state the View will be in when it is first created.
     */
    abstract val initialState: S

    private val viewMutableState: MutableStateFlow<S> by lazy {
        MutableStateFlow(initialState)
    }

    /**
     * A [StateFlow] that emits the current [MviViewState].
     * The UI should collect this flow to render its content.
     */
    val viewState: StateFlow<S> by lazy {
        viewMutableState.asStateFlow()
    }

    private val eventChannel = Channel<MviSideEffect<E>>(Channel.BUFFERED)

    /**
     * An internal [Flow] of [MviSideEffect] to be collected by the [MviScreen] wrapper.
     */
    internal val eventFlow: Flow<MviSideEffect<E>> = eventChannel.receiveAsFlow()

    /**
     * Entry point for user actions.
     * Launches a coroutine to process the [intent] asynchronously.
     *
     * @param intent The user action to be processed.
     */
    fun onIntent(intent: I) {
        safeLaunch {
            processIntent(intent)
        }
    }

    /**
     * Processes the given [intent].
     * Subclasses must implement this to handle specific business logic.
     *
     * @param intent The intent to process.
     */
    protected abstract suspend fun processIntent(intent: I)

    /**
     * Default implementation of error handling.
     * Maps the [throwable] to a message and sends it as a standard [MviSingleEvent.ShowSnackbar].
     */
    override fun handleError(throwable: Throwable) {
        val message = throwable.toMessage()
        safeLaunch {
            eventChannel.send(MviSideEffect.System(MviSingleEvent.ShowSnackbar(message, SnackbarType.ERROR)))
        }
    }

    /**
     * Reduces the current state by applying the [change].
     * This is the preferred and ONLY way to update the [viewState].
     */
    protected fun reduce(change: MviPartialStateChange<S>) {
        viewMutableState.update { change.reduce(it) }
    }

    /**
     * Sends a single event to the UI.
     * Use this for navigation, showing dialogs, or other one-time actions.
     */
    protected suspend fun sendEvent(event: E) {
        eventChannel.send(MviSideEffect.Feature(event))
    }

    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
    }
}
