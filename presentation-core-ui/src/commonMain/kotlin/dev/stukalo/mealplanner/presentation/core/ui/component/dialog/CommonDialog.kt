package dev.stukalo.mealplanner.presentation.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.component.button.text.TextButton

/**
 * A generic dialog with a title, message, and action buttons.
 *
 * @param title The title of the dialog.
 * @param message The main content message.
 * @param confirmLabel Label for the confirmation button.
 * @param onConfirm Callback when the confirmation button is clicked.
 * @param dismissLabel Optional label for the dismiss button.
 * @param onDismissRequest Callback when the dialog is dismissed or dismiss button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    dismissLabel: String? = null
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .fillMaxWidth()
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.background.secondary)
            .padding(Theme.spacing.space24)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = Theme.typography.bold16,
                color = Theme.color.text.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Theme.spacing.space16))

            Text(
                text = message,
                style = Theme.typography.regular14,
                color = Theme.color.text.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Theme.spacing.space24))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (dismissLabel != null) {
                    TextButton(
                        text = dismissLabel,
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(Theme.spacing.space12))
                }
                PrimaryButton(
                    text = confirmLabel,
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
