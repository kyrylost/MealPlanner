package dev.stukalo.mealplanner.presentation.core.ui.base.mvi

import dev.stukalo.mealplanner.presentation.core.ui.base.BaseViewModel
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviIntent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
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
 * IMPORTANT for AI: All feature ViewModels MUST inherit from this class.
 * Consult ARCHITECTURE.md for details on the Intent/State/Event flow.
 */
abstract class BaseMviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> : BaseViewModel() {
    abstract val initialState: S

    private val viewMutableState: MutableStateFlow<S> by lazy {
        MutableStateFlow(initialState)
    }

    val viewState: StateFlow<S> by lazy {
        viewMutableState.asStateFlow()
    }

    private val eventChannel = Channel<E>(Channel.UNLIMITED)
    internal val eventFlow: Flow<E> = eventChannel.receiveAsFlow()

    fun onIntent(intent: I) {
        launch {
            processIntent(intent)
        }
    }

    abstract suspend fun processIntent(intent: I)

    protected fun updateState(reducer: (S) -> S) {
        viewMutableState.update { reducer(it) }
    }

    protected suspend fun sendEvent(event: E) {
        eventChannel.send(event)
    }

    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
    }
}
