package dev.stukalo.mealplanner.presentation.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    /**
     * Launches a coroutine with optional loading, result, and error handling, and supports debouncing.
     *
     * This function launches a coroutine in the specified [coroutineScope] with the given [coroutineContext].
     * It provides hooks for loading, result, and error handling, and supports an optional debounce period.
     * The function is protected and intended to be used within a class or its subclasses.
     *
     * Usage:
     *
     * ```kotlin
     * launch(
     *     coroutineContext = Dispatchers.IO,
     *     coroutineScope = viewModelScope,
     *     request = { fetchData() },
     *     onLoading = { isLoading -> showLoadingIndicator(isLoading) },
     *     onResult = { result -> handleResult(result) },
     *     onError = { error -> showError(error) },
     *     debounce = 300L
     * )
     * ```
     *
     * @param T The type of the result returned by the [request] function.
     * @param coroutineContext The context in which the coroutine will be executed. Defaults to [kotlinx.coroutines.Dispatchers.IO].
     * @param coroutineScope The scope in which the coroutine will be launched. Defaults to [viewModelScope].
     * @param request The suspend function to be executed within the coroutine.
     * @param onLoading A callback to be invoked with the loading state. Can be null.
     * @param onResult A callback to be invoked with the result of the [request] function. Can be null.
     * @param onError A callback to be invoked with any errors that occur during the execution. Can be null.
     * @param debounce An optional debounce period in milliseconds.
     *                 If provided, the coroutine will delay execution by this amount.
     * @return The [kotlinx.coroutines.Job] representing the coroutine.
     */
    protected fun <T> launch(
        coroutineContext: CoroutineDispatcher = Dispatchers.IO,
        coroutineScope: CoroutineScope = viewModelScope,
        onLoading: (suspend (Boolean) -> Unit)? = null,
        onResult: (suspend (T?) -> Unit)? = null,
        // TODO: maybe, change to UiError whatever
        onError: (suspend (Throwable) -> Unit)? = null,
        debounce: Long? = null,
        request: suspend CoroutineScope.() -> T?,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            coroutineScope.launch {
                // TODO: maybe, change to UiError whatever
//                val uiError = handleException(throwable)
//                uiError?.let { onError?.invoke(it) }
                onError?.invoke(throwable)
                onLoading?.invoke(false)
            }
        }
        return coroutineScope.launch(
            context = exceptionHandler + coroutineContext,
        ) {
            debounce?.let { delay(it) }
            onLoading?.invoke(true)
            withContext(coroutineContext) { request() }.apply {
                this.let { result ->
                    onResult?.invoke(result)
                    onLoading?.invoke(false)
                }
            }
        }
    }

    /**
     * Emits a new value to a [kotlinx.coroutines.flow.StateFlow] if it is a [kotlinx.coroutines.flow.MutableStateFlow].
     *
     * This extension function allows emitting a new value to a [kotlinx.coroutines.flow.StateFlow] by casting it to a [kotlinx.coroutines.flow.MutableStateFlow].
     * If the cast is successful, the new value is emitted. This function is protected and intended to be used
     * within a class or its subclasses.
     *
     * Usage:
     *
     * ```kotlin
     * stateFlow.emitValue(newValue)
     * ```
     *
     * @param value The new value to be emitted.
     * @receiver The [kotlinx.coroutines.flow.StateFlow] to emit the value to.
     * @throws IllegalStateException if the [kotlinx.coroutines.flow.StateFlow] is not a [kotlinx.coroutines.flow.MutableStateFlow].
     */
// TODO: test
    protected suspend fun <T> StateFlow<T>.emitValue(value: T) {
        (this as? MutableStateFlow)?.emit(value)
    }

    /**
     * Emits a new value to a [kotlinx.coroutines.flow.SharedFlow] if it is a [kotlinx.coroutines.flow.MutableSharedFlow].
     *
     * This extension function allows emitting a new value to a [kotlinx.coroutines.flow.SharedFlow] by casting it to a [kotlinx.coroutines.flow.MutableSharedFlow].
     * If the cast is successful, the new value is emitted. This function is protected and intended to be used
     * within a class or its subclasses.
     *
     * Usage:
     *
     * ```kotlin
     * sharedFlow.emitValue(newValue)
     * ```
     *
     * @param value The new value to be emitted.
     * @receiver The [kotlinx.coroutines.flow.SharedFlow] to emit the value to.
     * @throws IllegalStateException if the [kotlinx.coroutines.flow.SharedFlow] is not a [kotlinx.coroutines.flow.MutableSharedFlow].
     */
// TODO: test
    protected suspend fun <T> SharedFlow<T>.emitValue(value: T) {
        (this as? MutableSharedFlow)?.emit(value)
    }

    /**
     * Updates the value of a [StateFlow] if it is a [MutableStateFlow].
     *
     * This extension function updates the current value of a [StateFlow] by casting it to a [MutableStateFlow]
     * and applying the provided update logic. This function is protected and intended to be used within a class
     * or its subclasses.
     *
     * @param T The type of the value held by the [StateFlow].
     * @param value The new value to be set in the [StateFlow].
     *
     * Usage:
     *
     * ```kotlin
     * stateFlow.updateValue(newValue)
     * ```
     */
// TODO: test
    protected fun <T> StateFlow<T>.updateValue(value: T) {
        (this as? MutableStateFlow)?.update { value }
    }
}