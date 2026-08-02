package dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model

import org.jetbrains.compose.resources.StringResource

data class SnackbarModel(val message: SnackbarMessage, val type: SnackbarType = SnackbarType.ERROR) {
    constructor(
        message: StringResource,
        type: SnackbarType = SnackbarType.ERROR
    ) : this(SnackbarMessage.Resource(message), type)

    constructor(
        message: String,
        type: SnackbarType = SnackbarType.ERROR
    ) : this(SnackbarMessage.Text(message), type)
}
