package dev.stukalo.mealplanner.presentation.feature.welcome.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.welcome_birth_date_label
import dev.stukalo.mealplanner.core.localization.welcome_birth_date_placeholder
import dev.stukalo.mealplanner.core.localization.welcome_continue_button
import dev.stukalo.mealplanner.core.localization.welcome_height_label
import dev.stukalo.mealplanner.core.localization.welcome_height_placeholder
import dev.stukalo.mealplanner.core.localization.welcome_name_label
import dev.stukalo.mealplanner.core.localization.welcome_name_placeholder
import dev.stukalo.mealplanner.core.localization.welcome_gender_female
import dev.stukalo.mealplanner.core.localization.welcome_gender_label
import dev.stukalo.mealplanner.core.localization.welcome_gender_male
import dev.stukalo.mealplanner.core.localization.welcome_weight_label
import dev.stukalo.mealplanner.core.localization.welcome_weight_placeholder
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core.PrimaryButtonSizeSet
import dev.stukalo.mealplanner.presentation.core.ui.widget.input.RoundedPlaceholderTextField
import dev.stukalo.mealplanner.presentation.core.ui.widget.selection.SelectionGroup
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun WelcomeData(
    modifier: Modifier = Modifier,
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    with(state) {
        Column(
            modifier = modifier
                .background(color = Theme.color.background)
        ) {
            Text(
                text = stringResource(Res.string.welcome_name_label),
                style = Theme.typography.bodyNormal,
                modifier = Modifier
                    .padding(
                        top = Theme.spacing.space24,
                        start = Theme.spacing.space28,
                        bottom = Theme.spacing.space12
                    )
            )
            RoundedPlaceholderTextField(
                value = TextFieldValue(nameInput, TextRange(nameInput.length)),
                onValueChange = {
                    onIntent(ViewIntent.OnChangeNameInputIntent(it.text))
                },
                placeholder = stringResource(Res.string.welcome_name_placeholder),
                error = nameErrorMessage?.let { stringResource(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.spacing.space24),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space24),
                modifier = Modifier
                    .padding(horizontal = Theme.spacing.space24)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(Res.string.welcome_birth_date_label),
                        style = Theme.typography.bodyNormal,
                        modifier = Modifier
                            .padding(
                                top = Theme.spacing.space24,
                                start = Theme.spacing.space4,
                                bottom = Theme.spacing.space12
                            )
                    )
                    RoundedPlaceholderTextField(
                        value = dateInput,
                        onValueChange = { },
                        placeholder = stringResource(Res.string.welcome_birth_date_placeholder),
                        enabled = false,
                        error = dateErrorMessage?.let { stringResource(it) },
                        onClick = {
                            onIntent(ViewIntent.OnShowDatePickerIntent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(Res.string.welcome_gender_label),
                        style = Theme.typography.bodyNormal,
                        modifier = Modifier
                            .padding(
                                top = Theme.spacing.space24,
                                start = Theme.spacing.space4,
                                bottom = Theme.spacing.space12
                            )
                    )
                    val male = stringResource(Res.string.welcome_gender_male)
                    val female = stringResource(Res.string.welcome_gender_female)
                    val options = listOf(male, female)
                    SelectionGroup(
                        options = options,
                        selectedOption = when (gender) {
                            GenderDomainModel.MALE -> male
                            GenderDomainModel.FEMALE -> female
                            null -> null
                        },
                        onOptionSelected = {
                            val selectedGender = if (it == male) GenderDomainModel.MALE else GenderDomainModel.FEMALE
                            onIntent(ViewIntent.OnChangeGenderInputIntent(selectedGender))
                            focusManager.clearFocus()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space24),
                modifier = Modifier
                    .padding(horizontal = Theme.spacing.space24)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(Res.string.welcome_height_label),
                        style = Theme.typography.bodyNormal,
                        modifier = Modifier
                            .padding(
                                top = Theme.spacing.space24,
                                start = Theme.spacing.space4,
                                bottom = Theme.spacing.space12
                            )
                    )
                    RoundedPlaceholderTextField(
                        value = TextFieldValue(heightInput, TextRange(heightInput.length)),
                        onValueChange = {
                            onIntent(ViewIntent.OnChangeHeightInputIntent(it.text))
                        },
                        placeholder = stringResource(Res.string.welcome_height_placeholder),
                        error = heightErrorMessage?.let { stringResource(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(Res.string.welcome_weight_label),
                        style = Theme.typography.bodyNormal,
                        modifier = Modifier
                            .padding(
                                top = Theme.spacing.space24,
                                start = Theme.spacing.space4,
                                bottom = Theme.spacing.space12
                            )
                    )
                    RoundedPlaceholderTextField(
                        value = TextFieldValue(weightInput, TextRange(weightInput.length)),
                        onValueChange = {
                            onIntent(ViewIntent.OnChangeWeightInputIntent(it.text))
                        },
                        placeholder = stringResource(Res.string.welcome_weight_placeholder),
                        error = weightErrorMessage?.let { stringResource(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = stringResource(Res.string.welcome_continue_button),
                onClick = {
                    onIntent(ViewIntent.OnContinueClickIntent)
                    focusManager.clearFocus()
                },
                sizes = PrimaryButtonSizeSet().default(),
                corner = Theme.spacing.space16,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(
                        horizontal = Theme.spacing.space24,
                        vertical = Theme.spacing.space24
                    ),
            )

            if (showDatePicker) {
                val datePickerState = rememberDatePickerState()

                DatePickerDialog(
                    onDismissRequest = {
                        onIntent(ViewIntent.OnHideDatePickerIntent)
                        focusManager.clearFocus()
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onIntent(ViewIntent.OnChangeDateInputIntent(datePickerState.selectedDateMillis))
                                onIntent(ViewIntent.OnHideDatePickerIntent)
                                focusManager.clearFocus()
                            }
                        ) {
                            Text(stringResource(Res.string.common_ok))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                onIntent(ViewIntent.OnHideDatePickerIntent)
                                focusManager.clearFocus()
                            }
                        ) {
                            Text(stringResource(Res.string.common_cancel))
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }
        }
    }
}
