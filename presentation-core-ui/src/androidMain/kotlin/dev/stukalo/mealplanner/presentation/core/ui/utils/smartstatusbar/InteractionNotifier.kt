package dev.stukalo.mealplanner.presentation.core.ui.utils.smartstatusbar

import kotlinx.coroutines.flow.MutableSharedFlow

internal val interactionFlow: MutableSharedFlow<Unit> = MutableSharedFlow()

/**
 * Notifies the smart status bar system about a user interaction.
 *
 * When using [RefreshPolicy.RefreshOnInteraction], this function should be called from a global
 * event hook (like `Activity.dispatchTouchEvent`) to trigger the status bar icon color analysis.
 *
 * ### Example in MainActivity
 * ```kotlin
 * override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
 *     lifecycleScope.launch {
 *         notifyAboutInteraction()
 *     }
 *     return super.dispatchTouchEvent(ev)
 * }
 * ```
 */
suspend fun notifyAboutInteraction() {
    interactionFlow.emit(Unit)
}
