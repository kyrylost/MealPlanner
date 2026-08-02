package dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model.AppSnackbarVisuals
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.model.SnackbarModel

@Composable
fun AppSnackbarHost(hostState: SnackbarHostState, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible = hostState.currentSnackbarData != null,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = modifier
    ) {
        SnackbarHost(hostState = hostState) { data ->
            val model =
                (data.visuals as? AppSnackbarVisuals)?.model
                    ?: SnackbarModel(message = data.visuals.message)

            AppSnackbar(model = model)
        }
    }
}
