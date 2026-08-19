package dev.stukalo.mealplanner.presentation.core.ui.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.SnackbarMessage
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.SnackbarModel
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.model.SnackbarType
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppSnackbar(model: SnackbarModel, modifier: Modifier = Modifier) {
    val backgroundColor =
        when (model.type) {
            SnackbarType.SUCCESS -> Theme.color.state.success
            SnackbarType.WARNING -> Theme.color.state.warning
            SnackbarType.ERROR -> Theme.color.state.error
        }

    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(Theme.spacing.space16)
            .clip(RoundedCornerShape(Theme.radius.radius12))
            .background(backgroundColor)
            .padding(Theme.spacing.space16)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text =
                when (val message = model.message) {
                    is SnackbarMessage.Resource -> stringResource(message.res)
                    is SnackbarMessage.Text -> message.value
                }
            Text(
                text = text,
                style = Theme.typography.regular14,
                color = Theme.color.text.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun SuccessSnackbarPreview() {
    Theme {
        AppSnackbar(
            model =
            SnackbarModel(
                message = "Success message",
                type = SnackbarType.SUCCESS
            )
        )
    }
}

@Preview
@Composable
private fun WarningSnackbarPreview() {
    Theme {
        AppSnackbar(
            model =
            SnackbarModel(
                message = "Warning message",
                type = SnackbarType.WARNING
            )
        )
    }
}

@Preview
@Composable
private fun ErrorSnackbarPreview() {
    Theme {
        AppSnackbar(
            model =
            SnackbarModel(
                message = "Error message",
                type = SnackbarType.ERROR
            )
        )
    }
}
