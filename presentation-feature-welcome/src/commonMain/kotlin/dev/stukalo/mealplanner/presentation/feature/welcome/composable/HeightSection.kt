package dev.stukalo.mealplanner.presentation.feature.welcome.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.welcome_height_label
import dev.stukalo.mealplanner.core.localization.welcome_height_placeholder
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.picker.RulerPicker
import dev.stukalo.mealplanner.presentation.core.ui.widget.picker.ValueEditDialog
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HeightSection(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    var showEditDialog by remember { mutableStateOf(false) }
    val heightValue = state.heightInput.toFloatOrNull() ?: 170f

    Column(modifier = modifier) {
        RulerPicker(
            label = stringResource(Res.string.welcome_height_label),
            value = heightValue,
            onValueChange = { onIntent(ViewIntent.OnChangeHeightInputIntent(it.toString())) },
            range = 50f..250f,
            unit = stringResource(Res.string.welcome_height_placeholder),
            onEditClick = { showEditDialog = true },
            modifier = Modifier.fillMaxWidth().padding(top = Theme.spacing.space24)
        )

        if (showEditDialog) {
            ValueEditDialog(
                initialValue = state.heightInput,
                onDismissRequest = { showEditDialog = false },
                onConfirm = { onIntent(ViewIntent.OnChangeHeightInputIntent(it)) },
                title = stringResource(Res.string.welcome_height_label),
                placeholder = stringResource(Res.string.welcome_height_placeholder),
                confirmLabel = stringResource(Res.string.common_ok),
                dismissLabel = stringResource(Res.string.common_cancel)
            )
        }

        state.heightErrorMessage?.let {
            Text(
                text = stringResource(it),
                color = Theme.color.error,
                style = Theme.typography.regular12,
                modifier = Modifier.padding(top = Theme.spacing.space4)
            )
        }
    }
}

@Preview
@Composable
private fun HeightSectionPreview() {
    Theme {
        HeightSection(
            state = ViewState(heightInput = "180"),
            onIntent = {}
        )
    }
}
