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
import dev.stukalo.mealplanner.core.localization.settings_target_weight
import dev.stukalo.mealplanner.core.localization.welcome_weight_unit_kg
import dev.stukalo.mealplanner.domain.model.user.UserConstants
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.dialog.ValueEditDialog
import dev.stukalo.mealplanner.presentation.core.ui.widget.picker.RulerPicker
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

/**
 * A section for entering the user's target weight.
 *
 * @param state The current view state.
 * @param onIntent Callback for processing user intents.
 * @param modifier The modifier to apply to the component.
 */
@Composable
internal fun TargetWeightSection(state: ViewState, onIntent: (ViewIntent) -> Unit, modifier: Modifier = Modifier) {
    var showEditDialog by remember { mutableStateOf(false) }
    val weightValue = state.targetWeightInput.toFloatOrNull() ?: 0f

    Column(modifier = modifier) {
        RulerPicker(
            label = stringResource(Res.string.settings_target_weight),
            value = weightValue,
            onValueChange = { onIntent(ViewIntent.OnChangeTargetWeightInputIntent(it.toString())) },
            range = UserConstants.MIN_WEIGHT.toFloat()..UserConstants.MAX_WEIGHT.toFloat(),
            unit = stringResource(Res.string.welcome_weight_unit_kg),
            onEditClick = { showEditDialog = true },
            modifier = Modifier.fillMaxWidth().padding(top = Theme.spacing.space24)
        )

        if (showEditDialog) {
            ValueEditDialog(
                initialValue = state.targetWeightInput,
                onDismissRequest = { showEditDialog = false },
                onConfirm = { onIntent(ViewIntent.OnChangeTargetWeightInputIntent(it)) },
                title = stringResource(Res.string.settings_target_weight),
                placeholder = stringResource(Res.string.welcome_weight_unit_kg),
                confirmLabel = stringResource(Res.string.common_ok),
                dismissLabel = stringResource(Res.string.common_cancel)
            )
        }

        state.targetWeightErrorMessage?.let {
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
private fun TargetWeightSectionPreview() {
    Theme {
        TargetWeightSection(
            state = ViewState(targetWeightInput = "70"),
            onIntent = {}
        )
    }
}
