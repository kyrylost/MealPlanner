package dev.stukalo.mealplanner.presentation.feature.welcome.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.welcome_birth_date_label
import dev.stukalo.mealplanner.core.localization.welcome_birth_date_placeholder
import dev.stukalo.mealplanner.core.localization.welcome_name_label
import dev.stukalo.mealplanner.core.localization.welcome_name_placeholder
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.input.RoundedPlaceholderTextField
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NameAndDateSection(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.welcome_name_label),
            style = Theme.typography.regular12,
            modifier = Modifier.padding(top = Theme.spacing.space24, bottom = Theme.spacing.space12)
        )
        RoundedPlaceholderTextField(
            value = TextFieldValue(state.nameInput, TextRange(state.nameInput.length)),
            onValueChange = { onIntent(ViewIntent.OnChangeNameInputIntent(it.text)) },
            placeholder = stringResource(Res.string.welcome_name_placeholder),
            error = state.nameErrorMessage?.let { stringResource(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(Res.string.welcome_birth_date_label),
            style = Theme.typography.regular12,
            modifier = Modifier.padding(top = Theme.spacing.space24, bottom = Theme.spacing.space12)
        )
        RoundedPlaceholderTextField(
            value = state.dateInput,
            onValueChange = { },
            placeholder = stringResource(Res.string.welcome_birth_date_placeholder),
            enabled = false,
            error = state.dateErrorMessage?.let { stringResource(it) },
            onClick = { onIntent(ViewIntent.OnShowDatePickerIntent) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun NameAndDateSectionPreview() {
    Theme {
        NameAndDateSection(
            state = ViewState(nameInput = "John Doe", dateInput = "01/01/1990"),
            onIntent = {}
        )
    }
}
