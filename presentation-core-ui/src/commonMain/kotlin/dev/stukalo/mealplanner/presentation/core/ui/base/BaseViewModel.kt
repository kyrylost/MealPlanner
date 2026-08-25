package dev.stukalo.mealplanner.presentation.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Base class for all ViewModels in the project.
 * Provides a standardized way to launch coroutines with error handling.
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * Global error handler for all coroutines launched via [safeLaunch].
     * Forwards exceptions to the [handleError] method.
     */
    protected open val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
    }

    /**
     * Handles exceptions thrown within coroutines launched via [safeLaunch].
     * Must be implemented by subclasses to provide UI feedback (e.g., showing a Snackbar).
     *
     * @param throwable The exception that occurred.
     */
    protected abstract fun handleError(throwable: Throwable)

    /**
     * Launches a coroutine in the [viewModelScope] with a [supervisorScope] and [coroutineExceptionHandler].
     * This is the preferred way to launch coroutines in ViewModels to ensure consistent error handling.
     *
     * @param coroutineContext Additional context for the coroutine.
     * @param block The suspendable block of code to execute.
     * @return The [Job] representing the launched coroutine.
     */
    protected fun safeLaunch(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(coroutineContext + coroutineExceptionHandler) {
        supervisorScope {
            block()
        }
    }
}
