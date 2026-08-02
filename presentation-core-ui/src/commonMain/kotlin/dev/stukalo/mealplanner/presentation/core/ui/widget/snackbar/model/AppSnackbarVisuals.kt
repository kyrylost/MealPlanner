package dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class AppSnackbarVisuals(
    val model: SnackbarModel,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val message: String = "",
    override val withDismissAction: Boolean = false
) : SnackbarVisuals
