package dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract

import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.SnackbarType
import org.jetbrains.compose.resources.StringResource

/**
 * Immutable object which represents a single event
 * like snack bar message, navigation event, a dialog trigger, etc...
 */
interface MviSingleEvent {
    /**
     * Standard event to show a snackbar with a localized message.
     *
     * @param message The localized string resource to display.
     * @param type The type of snackbar (e.g., SUCCESS, WARNING, ERROR).
     */
    data class ShowSnackbar(val message: StringResource, val type: SnackbarType = SnackbarType.ERROR) : MviSingleEvent
}
