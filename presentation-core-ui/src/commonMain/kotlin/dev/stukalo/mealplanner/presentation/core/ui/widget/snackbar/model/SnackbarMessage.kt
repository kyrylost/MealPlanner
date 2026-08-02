package dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model

import org.jetbrains.compose.resources.StringResource

sealed interface SnackbarMessage {
    data class Resource(val res: StringResource) : SnackbarMessage

    data class Text(val value: String) : SnackbarMessage
}
