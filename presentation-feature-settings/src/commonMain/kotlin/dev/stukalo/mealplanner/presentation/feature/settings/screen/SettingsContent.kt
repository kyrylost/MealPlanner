package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.common_settings
import dev.stukalo.mealplanner.core.localization.common_value_placeholder
import dev.stukalo.mealplanner.core.localization.home_steps
import dev.stukalo.mealplanner.core.localization.settings_health_sync
import dev.stukalo.mealplanner.core.localization.settings_health_sync_install
import dev.stukalo.mealplanner.core.localization.settings_health_sync_not_installed
import dev.stukalo.mealplanner.core.localization.settings_health_sync_not_supported
import dev.stukalo.mealplanner.core.localization.settings_health_sync_revoke_hint
import dev.stukalo.mealplanner.core.localization.settings_language
import dev.stukalo.mealplanner.core.localization.settings_language_en
import dev.stukalo.mealplanner.core.localization.settings_language_uk
import dev.stukalo.mealplanner.core.localization.settings_meal_reminders
import dev.stukalo.mealplanner.core.localization.settings_meal_reminders_desc
import dev.stukalo.mealplanner.core.localization.settings_my_profile
import dev.stukalo.mealplanner.core.localization.settings_notifications
import dev.stukalo.mealplanner.core.localization.settings_save_changes
import dev.stukalo.mealplanner.core.localization.settings_target_weight
import dev.stukalo.mealplanner.core.localization.settings_theme_color
import dev.stukalo.mealplanner.core.localization.settings_theme_mode
import dev.stukalo.mealplanner.core.localization.settings_theme_mode_auto
import dev.stukalo.mealplanner.core.localization.settings_theme_mode_dark
import dev.stukalo.mealplanner.core.localization.settings_theme_mode_light
import dev.stukalo.mealplanner.core.localization.welcome_activity_high_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_label
import dev.stukalo.mealplanner.core.localization.welcome_activity_low_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_medium_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_very_high_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_very_low_name
import dev.stukalo.mealplanner.core.localization.welcome_diet_balanced
import dev.stukalo.mealplanner.core.localization.welcome_diet_cutting
import dev.stukalo.mealplanner.core.localization.welcome_diet_label
import dev.stukalo.mealplanner.core.localization.welcome_diet_weight_gain
import dev.stukalo.mealplanner.core.localization.welcome_diet_weight_loss
import dev.stukalo.mealplanner.core.localization.welcome_height_label
import dev.stukalo.mealplanner.core.localization.welcome_height_unit_cm
import dev.stukalo.mealplanner.core.localization.welcome_weight_label
import dev.stukalo.mealplanner.core.localization.welcome_weight_unit_kg
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserConstants
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.component.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.component.dialog.CommonDialog
import dev.stukalo.mealplanner.presentation.core.ui.component.dialog.ValueEditDialog
import dev.stukalo.mealplanner.presentation.core.ui.component.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.component.picker.RulerPicker
import dev.stukalo.mealplanner.presentation.core.ui.component.row.SettingsOption
import dev.stukalo.mealplanner.presentation.core.ui.component.row.SettingsToggleOption
import dev.stukalo.mealplanner.presentation.core.ui.component.selector.SegmentedSelector
import dev.stukalo.mealplanner.presentation.feature.settings.component.ActivityLevelSelection
import dev.stukalo.mealplanner.presentation.feature.settings.component.DietTypeSelection
import dev.stukalo.mealplanner.presentation.feature.settings.component.HealthSyncToggle
import dev.stukalo.mealplanner.presentation.feature.settings.component.ThemeOption
import dev.stukalo.mealplanner.presentation.feature.settings.core.platform.healthSyncBlockedHint
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.EditableField
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

/**
 * The content of the Settings screen.
 *
 * @param state The current [ViewState].
 * @param onIntent Callback to handle user intents.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsContent(state: ViewState, onIntent: (ViewIntent) -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showLanguageSheet by remember { mutableStateOf(value = false) }
    var showRevokeDialog by remember { mutableStateOf(value = false) }

    if (showRevokeDialog) {
        CommonDialog(
            title = stringResource(Res.string.settings_health_sync),
            message = stringResource(Res.string.settings_health_sync_revoke_hint),
            confirmLabel = stringResource(Res.string.common_ok),
            onConfirm = {
                onIntent(ViewIntent.OnOpenHealthSettings)
                showRevokeDialog = false
            },
            onDismissRequest = { showRevokeDialog = false },
            dismissLabel = stringResource(Res.string.common_cancel)
        )
    }

    if (state.showPermissionBlockedDialog) {
        CommonDialog(
            title = stringResource(Res.string.settings_health_sync),
            message = stringResource(healthSyncBlockedHint),
            confirmLabel = stringResource(Res.string.common_ok),
            onConfirm = {
                onIntent(ViewIntent.OnOpenHealthSettings)
                onIntent(ViewIntent.OnDismissPermissionBlockedDialog)
            },
            onDismissRequest = { onIntent(ViewIntent.OnDismissPermissionBlockedDialog) },
            dismissLabel = stringResource(Res.string.common_cancel)
        )
    }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
    ) {
        CommonHeader(
            title = stringResource(Res.string.common_settings)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding =
            PaddingValues(
                start = Theme.spacing.space16,
                end = Theme.spacing.space16,
                top = Theme.spacing.space16,
                bottom = Theme.spacing.space16 + LocalBottomBarHeight.current
            ),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
        ) {
            item {
                Text(
                    text = stringResource(Res.string.settings_my_profile),
                    style = Theme.typography.bold14,
                    color = Theme.color.text.primary
                )
            }

            item {
                val user = state.user
                Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)) {
                    SettingsOption(
                        title = stringResource(Res.string.welcome_weight_label),
                        value = user?.weight?.let { "$it ${stringResource(Res.string.welcome_weight_unit_kg)}" },
                        onClick = { onIntent(ViewIntent.OnEditFieldClick(EditableField.Weight)) }
                    )
                    SettingsOption(
                        title = stringResource(Res.string.welcome_height_label),
                        value = user?.height?.let {
                            "${it.toInt()} ${stringResource(Res.string.welcome_height_unit_cm)}"
                        },
                        onClick = { onIntent(ViewIntent.OnEditFieldClick(EditableField.Height)) }
                    )
                    SettingsOption(
                        title = stringResource(Res.string.settings_target_weight),
                        value = user?.targetWeight?.let { "$it ${stringResource(Res.string.welcome_weight_unit_kg)}" },
                        onClick = { onIntent(ViewIntent.OnEditFieldClick(EditableField.TargetWeight)) }
                    )
                    SettingsOption(
                        title = stringResource(Res.string.home_steps),
                        value = user?.stepsTarget?.toString(),
                        onClick = { onIntent(ViewIntent.OnEditFieldClick(EditableField.StepsTarget)) }
                    )
                    SettingsOption(
                        title = stringResource(Res.string.welcome_activity_label),
                        value = user?.physicalActivity?.let { level ->
                            stringResource(
                                when (level) {
                                    ActivityLevelDomainModel.VERY_LOW -> Res.string.welcome_activity_very_low_name
                                    ActivityLevelDomainModel.LOW -> Res.string.welcome_activity_low_name
                                    ActivityLevelDomainModel.MEDIUM -> Res.string.welcome_activity_medium_name
                                    ActivityLevelDomainModel.HIGH -> Res.string.welcome_activity_high_name
                                    ActivityLevelDomainModel.VERY_HIGH -> Res.string.welcome_activity_very_high_name
                                }
                            )
                        },
                        onClick = { onIntent(ViewIntent.OnEditFieldClick(EditableField.ActivityLevel)) }
                    )
                    SettingsOption(
                        title = stringResource(Res.string.welcome_diet_label),
                        value = user?.diet?.let { diet ->
                            stringResource(
                                when (diet) {
                                    DietDomainModel.BALANCED_DIET -> Res.string.welcome_diet_balanced
                                    DietDomainModel.WEIGHT_GAIN -> Res.string.welcome_diet_weight_gain
                                    DietDomainModel.WEIGHT_LOSS -> Res.string.welcome_diet_weight_loss
                                    DietDomainModel.CUTTING_DIET -> Res.string.welcome_diet_cutting
                                }
                            )
                        },
                        onClick = { onIntent(ViewIntent.OnEditFieldClick(EditableField.DietType)) }
                    )
                }
            }

            item {
                Text(
                    text = stringResource(Res.string.settings_language),
                    style = Theme.typography.bold14,
                    color = Theme.color.text.primary
                )
            }

            item {
                val languageName = when (state.currentLanguage) {
                    ViewState.LOCALE_UK -> stringResource(Res.string.settings_language_uk)
                    else -> stringResource(Res.string.settings_language_en)
                }
                SettingsOption(
                    title = stringResource(Res.string.settings_language),
                    value = languageName,
                    onClick = { showLanguageSheet = true }
                )
            }

            item {
                Text(
                    text = stringResource(Res.string.settings_theme_mode),
                    style = Theme.typography.bold14,
                    color = Theme.color.text.primary
                )
            }

            item {
                SegmentedSelector(
                    items = ThemeModeDomainModel.entries,
                    selectedItem = state.currentThemeMode,
                    onItemSelected = { onIntent(ViewIntent.OnThemeModeClick(it)) },
                    label = { mode ->
                        stringResource(
                            when (mode) {
                                ThemeModeDomainModel.AUTO -> Res.string.settings_theme_mode_auto
                                ThemeModeDomainModel.LIGHT -> Res.string.settings_theme_mode_light
                                ThemeModeDomainModel.DARK -> Res.string.settings_theme_mode_dark
                            }
                        )
                    }
                )
            }

            item {
                Text(
                    text = stringResource(Res.string.settings_theme_color),
                    style = Theme.typography.bold14,
                    color = Theme.color.text.primary
                )
            }

            items(ColorPaletteDomainModel.entries) { palette ->
                ThemeOption(
                    palette = palette,
                    isSelected = state.currentColorPalette == palette,
                    onClick = {
                        onIntent(ViewIntent.OnColorPaletteClick(palette))
                    }
                )
            }

            item {
                Text(
                    text = stringResource(Res.string.settings_notifications),
                    style = Theme.typography.bold14,
                    color = Theme.color.text.primary
                )
            }

            item {
                SettingsToggleOption(
                    title = stringResource(Res.string.settings_meal_reminders),
                    description = stringResource(Res.string.settings_meal_reminders_desc),
                    checked = state.isMealRemindersEnabled,
                    onCheckedChange = { onIntent(ViewIntent.OnMealRemindersToggle(it)) }
                )
            }

            item {
                Text(
                    text = stringResource(Res.string.settings_health_sync),
                    style = Theme.typography.bold14,
                    color = Theme.color.text.primary
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)) {
                    when (state.healthServiceStatus) {
                        HealthServiceStatus.AVAILABLE -> {
                            state.permissionOptions.forEach { option ->
                                HealthSyncToggle(
                                    option = option,
                                    onIntent = onIntent,
                                    onShowRevokeDialog = { showRevokeDialog = true }
                                )
                            }

                            Spacer(modifier = Modifier.height(Theme.spacing.space8))

                            Text(
                                text = stringResource(Res.string.settings_health_sync_revoke_hint),
                                style = Theme.typography.regular12,
                                color = Theme.color.text.secondary,
                                modifier = Modifier.padding(horizontal = Theme.spacing.space4)
                            )
                        }
                        HealthServiceStatus.NOT_INSTALLED -> {
                            Text(
                                text = stringResource(Res.string.settings_health_sync_not_installed),
                                style = Theme.typography.regular12,
                                color = Theme.color.text.secondary,
                                modifier = Modifier.padding(horizontal = Theme.spacing.space4)
                            )
                            PrimaryButton(
                                text = stringResource(Res.string.settings_health_sync_install),
                                onClick = { onIntent(ViewIntent.OnInstallHealthConnectClick) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        HealthServiceStatus.NOT_SUPPORTED -> {
                            Text(
                                text = stringResource(Res.string.settings_health_sync_not_supported),
                                style = Theme.typography.regular12,
                                color = Theme.color.text.secondary,
                                modifier = Modifier.padding(horizontal = Theme.spacing.space4)
                            )
                        }
                    }
                }
            }
        }
    }

    if (state.editingField != null && !state.isManualInputVisible) {
        ModalBottomSheet(
            onDismissRequest = { onIntent(ViewIntent.OnDismissEdit) },
            sheetState = sheetState,
            containerColor = Theme.color.background.secondary,
            contentColor = Theme.color.text.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(Theme.spacing.space24)
            ) {
                when (state.editingField) {
                    EditableField.Weight -> {
                        RulerPicker(
                            label = stringResource(Res.string.welcome_weight_label),
                            value =
                            state.tempWeightInput.toFloatOrNull() ?: state.user?.weight?.toFloat()
                                ?: UserConstants.DEFAULT_WEIGHT.toFloat(),
                            onValueChange = { onIntent(ViewIntent.OnWeightChange(it.toString())) },
                            range = UserConstants.MIN_WEIGHT.toFloat()..UserConstants.MAX_WEIGHT.toFloat(),
                            unit = stringResource(Res.string.welcome_weight_unit_kg),
                            onEditClick = { onIntent(ViewIntent.OnManualInputClick) }
                        )
                    }
                    EditableField.Height -> {
                        RulerPicker(
                            label = stringResource(Res.string.welcome_height_label),
                            value =
                            state.tempHeightInput.toFloatOrNull() ?: state.user?.height?.toFloat()
                                ?: UserConstants.DEFAULT_HEIGHT.toFloat(),
                            onValueChange = { onIntent(ViewIntent.OnHeightChange(it.toString())) },
                            range = UserConstants.MIN_HEIGHT.toFloat()..UserConstants.MAX_HEIGHT.toFloat(),
                            unit = stringResource(Res.string.welcome_height_unit_cm),
                            onEditClick = { onIntent(ViewIntent.OnManualInputClick) }
                        )
                    }
                    EditableField.TargetWeight -> {
                        RulerPicker(
                            label = stringResource(Res.string.settings_target_weight),
                            value =
                            state.tempTargetWeightInput.toFloatOrNull() ?: state.user?.targetWeight?.toFloat()
                                ?: UserConstants.DEFAULT_WEIGHT.toFloat(),
                            onValueChange = { onIntent(ViewIntent.OnTargetWeightChange(it.toString())) },
                            range = UserConstants.MIN_WEIGHT.toFloat()..UserConstants.MAX_WEIGHT.toFloat(),
                            unit = stringResource(Res.string.welcome_weight_unit_kg),
                            onEditClick = { onIntent(ViewIntent.OnManualInputClick) }
                        )
                    }
                    EditableField.StepsTarget -> {
                        RulerPicker(
                            label = stringResource(Res.string.home_steps),
                            value =
                            state.tempStepsTargetInput.toFloatOrNull() ?: state.user?.stepsTarget?.toFloat()
                                ?: UserConstants.DEFAULT_STEPS_TARGET.toFloat(),
                            onValueChange = { onIntent(ViewIntent.OnStepsTargetChange(it.toInt().toString())) },
                            range = UserConstants.MIN_STEPS_TARGET.toFloat()..UserConstants.MAX_STEPS_TARGET.toFloat(),
                            step = UserConstants.STEPS_INCREMENT.toFloat(),
                            unit = "",
                            onEditClick = { onIntent(ViewIntent.OnManualInputClick) }
                        )
                    }
                    EditableField.ActivityLevel -> {
                        ActivityLevelSelection(
                            selectedLevel = state.user?.physicalActivity,
                            onLevelSelected = { onIntent(ViewIntent.OnActivityLevelChange(it)) }
                        )
                    }
                    EditableField.DietType -> {
                        DietTypeSelection(
                            selectedDiet = state.user?.diet,
                            onDietSelected = { onIntent(ViewIntent.OnDietTypeChange(it)) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Theme.spacing.space24))

                if ((state.editingField is EditableField.Weight) ||
                    (state.editingField is EditableField.Height) ||
                    (state.editingField is EditableField.TargetWeight) ||
                    (state.editingField is EditableField.StepsTarget)
                ) {
                    PrimaryButton(
                        text = stringResource(Res.string.settings_save_changes),
                        onClick = { onIntent(ViewIntent.OnSaveProfileClick) },
                        isLoading = state.isSaving,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(Theme.spacing.space24))
            }
        }
    }

    if (state.editingField != null && state.isManualInputVisible) {
        val titleRes = when (state.editingField) {
            EditableField.Weight -> Res.string.welcome_weight_label
            EditableField.Height -> Res.string.welcome_height_label
            EditableField.TargetWeight -> Res.string.settings_target_weight
            EditableField.StepsTarget -> Res.string.home_steps
            else -> null
        }

        val initialValue = when (state.editingField) {
            EditableField.Weight -> state.user?.weight?.toString().orEmpty()
            EditableField.Height -> state.user?.height?.toInt()?.toString().orEmpty()
            EditableField.TargetWeight -> state.user?.targetWeight?.toString().orEmpty()
            EditableField.StepsTarget -> state.user?.stepsTarget?.toString().orEmpty()
            else -> ""
        }

        if (titleRes != null) {
            ValueEditDialog(
                initialValue = initialValue,
                onDismissRequest = { onIntent(ViewIntent.OnDismissEdit) },
                onConfirm = { newValue ->
                    onIntent(ViewIntent.OnManualInputConfirm(newValue))
                },
                title = stringResource(titleRes),
                placeholder = stringResource(Res.string.common_value_placeholder),
                confirmLabel = stringResource(Res.string.settings_save_changes),
                dismissLabel = stringResource(Res.string.common_cancel),
                message = state.errorMessage?.let { stringResource(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }
    }

    if (showLanguageSheet) {
        ModalBottomSheet(
            onDismissRequest = { showLanguageSheet = false },
            sheetState = sheetState,
            containerColor = Theme.color.background.secondary,
            contentColor = Theme.color.text.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Theme.spacing.space24),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)
            ) {
                SettingsOption(
                    title = stringResource(Res.string.settings_language_en),
                    onClick = {
                        onIntent(ViewIntent.OnLanguageClick(ViewState.LOCALE_EN))
                        showLanguageSheet = false
                    }
                )
                SettingsOption(
                    title = stringResource(Res.string.settings_language_uk),
                    onClick = {
                        onIntent(ViewIntent.OnLanguageClick(ViewState.LOCALE_UK))
                        showLanguageSheet = false
                    }
                )
                Spacer(modifier = Modifier.height(Theme.spacing.space24))
            }
        }
    }
}

@Preview
@Composable
private fun SettingsContentPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            SettingsContent(
                state = ViewState(),
                onIntent = {}
            )
        }
    }
}
