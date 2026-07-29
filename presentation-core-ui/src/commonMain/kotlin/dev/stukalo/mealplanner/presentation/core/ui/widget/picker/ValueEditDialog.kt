package dev.stukalo.mealplanner.presentation.core.ui.widget.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.text.TextButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.input.RoundedPlaceholderTextField

@Composable
fun ValueEditDialog(
    initialValue: String,
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    title: String,
    placeholder: String,
    confirmLabel: String,
    dismissLabel: String,
    message: String? = null,
) {
    var textValue by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)) {
                Text(
                    text = title,
                    style = Theme.typography.bold16,
                    color = Theme.color.textPrimary
                )
                if (message != null) {
                    Text(
                        text = message,
                        style = Theme.typography.regular12,
                        color = Theme.color.textSecondary
                    )
                }
            }
        },
        text = {
            RoundedPlaceholderTextField(
                value = textValue,
                onValueChange = { textValue = it },
                placeholder = placeholder,
                textStyle = Theme.typography.regular12,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                text = confirmLabel,
                onClick = {
                    onConfirm(textValue)
                    onDismissRequest()
                }
            )
        },
        dismissButton = {
            TextButton(
                text = dismissLabel,
                onClick = onDismissRequest
            )
        },
        containerColor = Theme.color.backgroundSecondary,
        shape = Theme.shape.normalRoundedCornerShape
    )
}
