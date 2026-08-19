package dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviSingleEvent
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.SnackbarType
import org.jetbrains.compose.resources.StringResource

internal sealed interface ViewEvent : MviSingleEvent {
    data class ShowSnackbar(val message: StringResource, val type: SnackbarType) : ViewEvent

    data object NavigateToMainScreen : ViewEvent
}
