package dev.stukalo.mealplanner.presentation.feature.welcome.component

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
import dev.stukalo.mealplanner.core.localization.welcome_weight_label
import dev.stukalo.mealplanner.core.localization.welcome_weight_unit_kg
import dev.stukalo.mealplanner.domain.model.user.UserConstants
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.dialog.ValueEditDialog
import dev.stukalo.mealplanner.presentation.core.ui.component.picker.RulerPicker
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WeightSection(state: ViewState, onIntent: (ViewIntent) -> Unit, modifier: Modifier = Modifier) {
    var showEditDialog by remember { mutableStateOf(false) }
    val weightValue = state.weightInput.toFloatOrNull() ?: 0f

    Column(modifier = modifier) {
        RulerPicker(
            label = stringResource(Res.string.welcome_weight_label),
            value = weightValue,
            onValueChange = { onIntent(ViewIntent.OnChangeWeightInputIntent(it.toString())) },
            range = UserConstants.MIN_WEIGHT.toFloat()..UserConstants.MAX_WEIGHT.toFloat(),
            unit = stringResource(Res.string.welcome_weight_unit_kg), // Assuming this is "kg" or similar
            onEditClick = { showEditDialog = true },
            modifier = Modifier.fillMaxWidth().padding(top = Theme.spacing.space24)
        )

        if (showEditDialog) {
            ValueEditDialog(
                initialValue = state.weightInput,
                onDismissRequest = { showEditDialog = false },
                onConfirm = { onIntent(ViewIntent.OnChangeWeightInputIntent(it)) },
                title = stringResource(Res.string.welcome_weight_label),
                placeholder = stringResource(Res.string.welcome_weight_unit_kg),
                confirmLabel = stringResource(Res.string.common_ok),
                dismissLabel = stringResource(Res.string.common_cancel)
            )
        }

        state.weightErrorMessage?.let {
            Text(
                text = stringResource(it),
                color = Theme.color.state.error,
                style = Theme.typography.regular12,
                modifier = Modifier.padding(top = Theme.spacing.space4)
            )
        }
    }
}

@Preview
@Composable
private fun WeightSectionPreview() {
    Theme {
        WeightSection(
            state = ViewState(weightInput = "70"),
            onIntent = {}
        )
    }
}
